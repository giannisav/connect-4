package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.AuthenticationDetails;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.entities.Player;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.exceptions.*;
import com.ihu.Connect_4.mappers.GameMapper;
import com.ihu.Connect_4.repositories.AuthenticationDetailsRepository;
import com.ihu.Connect_4.repositories.GameRepository;
import com.ihu.Connect_4.utils.BoardUtil;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final PlayerService playerService;
    private final GameMapper mapper;
    private final BoardUtil boardUtil;
    private final CheatService cheatService;
    private final AuthenticationDetailsRepository authRepository;

    public GameServiceImpl(GameRepository repository, PlayerService playerService, GameMapper mapper,
                           BoardUtil boardUtil, CheatService cheatService, AuthenticationDetailsRepository authRepository) {
        this.repository = repository;
        this.playerService = playerService;
        this.mapper = mapper;
        this.boardUtil = boardUtil;
        this.cheatService = cheatService;
        this.authRepository = authRepository;
    }

    @Transactional
    @Override
    public GameDTO createGame(String nickname, boolean isVsAi) {
        Player player = playerService.fetchPlayer(nickname);
        Game game = new Game();
        game.setYellowPlayer(player);
        game.setNextMoveNickname("Wait your opponent to connect");
        game.setBoardMoves("");
        game.setIsVsAi(isVsAi);
        String uuid = UUID.randomUUID().toString();
        authRepository.save(new AuthenticationDetails(uuid, nickname, game));
        return mapper.mapToGameDTOWithUuid(repository.save(game), uuid);
    }

    @Transactional
    @Override
    public GameDTO joinGame(String nickname, Long id) {
        Game game = fetchGame(id);
        Player player = playerService.fetchPlayer(nickname);
        if (game.getYellowPlayer() != null && game.getRedPlayer() != null) {
            throw new FullGameException("Sorry this game is full");
        }
        game.setNextMoveNickname(game.getYellowPlayer().getNickname());
        game.setRedPlayer(player);
        game.setGameState(GameState.RUNNING);
        String uuid = UUID.randomUUID().toString();
        authRepository.save(new AuthenticationDetails(uuid, nickname, game));
        return mapper.mapToGameDTOWithUuid(repository.save(game), uuid);
    }

    @Transactional
    @Override
    public GameDTO play(String nickname, String uuid, Long id, Integer column) {
        Game game = fetchGame(id);
        checkRules(nickname, uuid, game, column);
        if (boardUtil.moveIsWinning(game.getBoardMoves(), column)) {
            return gameHasWinner(game, nickname, column, uuid);
        }
        if (boardUtil.gameIsDrawIfMoveIsNotWinning(game.getBoardMoves())) {
            return resultIsDraw(game, column, uuid);
        }
        String nextMoveNickname = nickname.equals(game.getYellowPlayer().getNickname()) ? game.getRedPlayer().getNickname() : game.getYellowPlayer().getNickname();
        game.setNextMoveNickname(nextMoveNickname);
        game.setBoardMoves(game.getBoardMoves() + column);
        return mapper.mapToGameDTOWithUuid(repository.save(game), uuid);
    }

    @Transactional
    @Override
    public GameDTO getGameStatus(String nickname, String uuid, Long id) {
        Game game = fetchGame(id);
        authenticatePlayer(nickname, uuid, game);
        return mapper.mapToGameDTOWithUuid(game, uuid);
    }

    @Transactional
    @Override
    public GameDTO cheatPlay(String nickname, String uuid, Long id) {
        Game game = fetchGame(id);
        Integer bestValidColumn = cheatService.getBestMove(game.getBoardMoves());
        return play(nickname, uuid, id, bestValidColumn);
    }

    @Override
    public List<GameDTO> findAvailableGames() {
        LocalDateTime now = LocalDateTime.now();
        return repository.findAllByGameStateEquals(GameState.CREATED)
                .stream()
                .filter(game -> Math.abs(Duration.between(now, game.getCreatedAt()).toMinutes()) <= 10)
                .map(mapper::mapToGameDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean needsUpdate(Long id, int numOfMoves) {
        Game game = fetchGame(id);
        if (game.getGameState() == GameState.RUNNING && game.getBoardMoves().length() == 0) {
            return true;
        } else {
            return !(game.getBoardMoves().length() == numOfMoves);
        }
    }

    private GameDTO gameHasWinner(Game game, String nickname, Integer column, String uuid) {
        game.setNextMoveNickname("Our winner is: " + nickname);
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        Player yellow = game.getYellowPlayer();
        Player red = game.getRedPlayer();
        if (nickname.equals(yellow.getNickname())) {
            winnerSettings(yellow, game.getIsVsAi());
            loserSettings(red);
        } else {
            winnerSettings(red, false);
            loserSettings(yellow);
        }
        return mapper.mapToGameDTOWithUuid(repository.save(game), uuid);
    }

    private GameDTO resultIsDraw(Game game, Integer column, String uuid) {
        game.setNextMoveNickname("Result is a draw");
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        drawSettings(game.getYellowPlayer());
        drawSettings(game.getRedPlayer());
        return mapper.mapToGameDTOWithUuid(repository.save(game), uuid);
    }

    private void checkRules(String nickname, String uuid, Game game, Integer column){
        authenticatePlayer(nickname, uuid, game);
        if (game.getGameState() != GameState.RUNNING) {
            if(game.getBoardMoves().length() == 0) {
                throw new InvalidTurnPlayException("Sorry, you can't play, you are alone in this room");
            } else {
                throw new InvalidTurnPlayException("The game has finished");
            }
        }
        if (!game.getNextMoveNickname().equals(nickname)) {
            throw new InvalidTurnPlayException("Its not your turn! Wait your opponent to play");
        }
        if (boardUtil.moveIsInvalid(game.getBoardMoves(), column)) {
            throw new InvalidMoveException("Invalid move! Please choose another column");
        }
    }

    public Game fetchGame(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotExistingGameException("There is no game with id: " + id));
    }

    private void winnerSettings(Player player, boolean isVsAi) {
        player.setPoints(player.getPoints() + 3);
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        player.setWins(player.getWins() + 1);
        if (isVsAi) {
            player.setWinsVsAi(player.getWinsVsAi() + 1);
        }
    }

    private void loserSettings(Player player) {
        player.setPoints(player.getPoints() - 3);
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        player.setLoses(player.getLoses() + 1);
    }

    private void drawSettings(Player player){
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        player.setDraws(player.getDraws() + 1);
    }

    private void authenticatePlayer(String nickname, String uuid, Game game) {
        authRepository.findByNicknameAndUuidAndGameEquals(nickname, uuid, game)
                .orElseThrow(() -> new UnauthorizedPlayerException("You are not authorized to do this"));
    }
}
