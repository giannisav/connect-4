package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.entities.Player;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.exceptions.*;
import com.ihu.Connect_4.mappers.GameMapper;
import com.ihu.Connect_4.repositories.GameRepository;
import com.ihu.Connect_4.repositories.PlayerRepository;
import com.ihu.Connect_4.utils.BoardUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final PlayerRepository playerRepository;
    private final GameMapper mapper;
    private final BoardUtil boardUtil;
    private final CheatService cheatService;

    public GameServiceImpl(GameRepository repository, PlayerRepository playerRepository, GameMapper mapper, BoardUtil boardUtil, CheatService cheatService) {
        this.repository = repository;
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.boardUtil = boardUtil;
        this.cheatService = cheatService;
    }

    @Transactional
    @Override
    public GameResponseDTO createGame(String nickname) {
        Player player = validatePlayerExists(nickname);
        Game game = new Game();
        player.setUuid(UUID.randomUUID().toString());
        game.setYellowPlayer(player);
        game.setNextMoveNickname("Wait your opponent to connect");
        game.setBoardMoves("");
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    @Transactional
    @Override
    public GameResponseDTO joinGame(String nickname, Long id) {
        Game game = validateGameExists(id);
        Player player = validatePlayerExists(nickname);
        if(nickname.equals(game.getYellowPlayer().getNickname())) {
            throw new UnauthorizedPlayerException("Sorry you are not allowed to win your self");
        }
        if(null != game.getYellowPlayer() && null != game.getRedPlayer()) {
            throw new FullGameException("Sorry this game is full");
        }
        player.setUuid(UUID.randomUUID().toString());
        game.setNextMoveNickname(game.getYellowPlayer().getNickname());
        game.setRedPlayer(player);
        game.setGameState(GameState.RUNNING);
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    @Transactional
    @Override
    public GameResponseDTO play(String nickname,String uuid, Long id, Integer column) {
        Game game = validateGameExists(id);
        checkRules(nickname, uuid, game, column);
        if(boardUtil.moveIsWinning(game.getBoardMoves(), column)) {
            return gameHasWinner(game, nickname, column);
        }
        if(boardUtil.gameIsDrawIfMoveIsNotWinning(game.getBoardMoves())) {
            return resultIsDraw(game, column);
        }
        String nextMoveNickname = nickname.equals(game.getYellowPlayer().getNickname()) ? game.getRedPlayer().getNickname() : game.getYellowPlayer().getNickname();
        game.setNextMoveNickname(nextMoveNickname);
        game.setBoardMoves(game.getBoardMoves() + column);
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    @Transactional
    @Override
    public GameResponseDTO getGameStatus(String nickname, Long id) {
        Game game = validateGameExists(id);
        if(!(game.getYellowPlayer().getNickname().equals(nickname) || game.getRedPlayer().getNickname().equals(nickname))) {
            throw new UnauthorizedPlayerException("You are not authorized to watch this game");
        }
        return mapper.mapToGameResponseDTO(game);
    }

    @Transactional
    @Override
    public GameResponseDTO cheatPlay(String nickname, String uuid, Long id) {
        Game game = validateGameExists(id);
        Integer bestValidColumn = cheatService.getBestMove(game.getBoardMoves());
        return play(nickname, uuid, id, bestValidColumn);
    }

    @Override
    public List<GameResponseDTO> findAvailableGames() {
        LocalDateTime now = LocalDateTime.now();
        return repository.findAllByGameStateEquals(GameState.CREATED)
                .stream()
                .filter(game -> Math.abs(Duration.between(now, game.getCreatedAt()).toMinutes()) <= 10)
                .map(game -> mapper.mapToGameResponseDTO(game))
                .collect(Collectors.toList());
    }

    private GameResponseDTO gameHasWinner(Game game, String nickname, Integer column) {
        game.setNextMoveNickname("Our winner is: " + nickname);
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        Player yellow = game.getYellowPlayer();
        Player red = game.getRedPlayer();
        if(nickname.equals(yellow.getNickname())) {
            winnerSettings(yellow);
            loserSettings(red);
        } else {
            winnerSettings(red);
            loserSettings(yellow);
        }
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    private GameResponseDTO resultIsDraw(Game game, Integer column) {
        game.setNextMoveNickname("Result is a draw");
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        drawSettings(game.getYellowPlayer());
        drawSettings(game.getRedPlayer());
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    private void checkRules(String nickname,String token, Game game, Integer column){
        Player player = validatePlayerExists(nickname);
        if(!(game.getYellowPlayer().getNickname().equals(nickname) || game.getRedPlayer().getNickname().equals(nickname))) {
            throw new UnauthorizedPlayerException("You are not authorized to play to this game");
        }
        if(!player.getUuid().equals(token)) {
            throw new UnauthorizedPlayerException("Not authenticated as " + nickname);
        }
        if(game.getGameState()!= GameState.RUNNING) {
            throw new InvalidTurnPlayException("Hmmm, you dont have opponent or the game has winner");
        }
        if(!game.getNextMoveNickname().equals(nickname)) {
            throw new InvalidTurnPlayException("Its not your turn! Wait your opponent to play");
        }
        if(boardUtil.moveIsInvalid(game.getBoardMoves(), column)) {
            throw new InvalidMoveException("Invalid move! Please choose another column");
        }
    }

    private Game validateGameExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotExistingGameException("There is no game with id: " + id));
    }

    private Player validatePlayerExists(String nickname) {
        return playerRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotExistingPlayerException("There is no player with nickname: " + nickname));
    }

    private void winnerSettings(Player player) {
        player.setPoints(player.getPoints() + 3);
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        player.setWins(player.getWins() + 1);
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
}
