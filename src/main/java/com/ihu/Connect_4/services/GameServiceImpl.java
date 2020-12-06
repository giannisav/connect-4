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
import java.util.Optional;

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
        Game game = new Game();
        Player player = Optional.ofNullable(playerRepository.findByNickname(nickname))
                .orElseThrow(() -> new NotExistingPlayerException("There is no player with nickname: " + nickname));
        game.setYellowPlayer(player);
        game.setNextMoveNickname("Waiting the opponent to connect");
        game.setBoardMoves("");
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    @Transactional
    @Override
    public GameResponseDTO joinGame(String nickname, Long id) {
        Game game = repository.findById(id)
                .orElseThrow(() ->new NotExistingGameException("There is no game with id: " + id));
        Player player = Optional.ofNullable(playerRepository.findByNickname(nickname))
                .orElseThrow(() -> new NotExistingPlayerException("There is no player with nickname: " + nickname));
        if(nickname.equals(game.getYellowPlayer().getNickname())) {
            throw new UnauthorizedPlayerException("Sorry you are not allowed to win your self!  ");
        }
        if(null != game.getYellowPlayer() && null != game.getRedPlayer()) {
            throw new FullGameException("Sorry this game is full.");
        }
        game.setNextMoveNickname(game.getYellowPlayer().getNickname());
        game.setRedPlayer(player);
        game.setGameState(GameState.RUNNING);
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    @Transactional
    @Override
    public GameResponseDTO play(String nickname, Long id, int column) {
        Game game = repository.findById(id)
                .orElseThrow(() ->new NotExistingGameException("There is no game with id: " + id));
        if(!(game.getYellowPlayer().getNickname().equals(nickname) || game.getRedPlayer().getNickname().equals(nickname))) {
            throw new UnauthorizedPlayerException("You are not authorized to play to this game");
        }
        if(game.getGameState()!= GameState.RUNNING) {
            throw new InvalidTurnPlayException("Its not your turn to play");
        }
        if(!game.getNextMoveNickname().equals(nickname)) {
            throw new InvalidTurnPlayException("Its not your turn! Wait your opponent to play");
        }
        if(boardUtil.moveIsInvalid(game.getBoardMoves(), column)) {
            throw new InvalidMoveException("Invalid move! Please choose another");
        }
        if(boardUtil.moveIsWinning(game.getBoardMoves(), column)) {
            return gameHasWinner(game, nickname, column);
        }
        if(game.getBoardMoves().length() == 41) {
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
        Game game = repository.findById(id)
                .orElseThrow(() ->new NotExistingGameException("There is no game with id: " + id));
        if(!(game.getYellowPlayer().getNickname().equals(nickname) || game.getRedPlayer().getNickname().equals(nickname))) {
            throw new UnauthorizedPlayerException("You are not authorized to play to this game");
        }
        return mapper.mapToGameResponseDTO(game);
    }

    @Transactional
    @Override
    public GameResponseDTO cheatPlay(String nickname, Long id) {
        Game game = repository.findById(id)
                .orElseThrow(() ->new NotExistingGameException("There is no game with id: " + id));
        if(!(game.getYellowPlayer().getNickname().equals(nickname) || game.getRedPlayer().getNickname().equals(nickname))) {
            throw new UnauthorizedPlayerException("You are not authorized to play to this game");
        }
        int bestValidColumn = cheatService.getBestMove(game.getBoardMoves());
        return play(nickname, id, bestValidColumn);
    }

    private GameResponseDTO gameHasWinner(Game game, String nickname, Integer column) {
        game.setNextMoveNickname("Our winner is: " + nickname);
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        if(nickname.equals(game.getYellowPlayer().getNickname())) {
            game.getYellowPlayer().setPoints(game.getYellowPlayer().getPoints() + 3);
            game.getYellowPlayer().setGamesPlayed(game.getYellowPlayer().getGamesPlayed() + 1);
            game.getYellowPlayer().setWins(game.getYellowPlayer().getWins() + 1);
            game.getRedPlayer().setPoints(game.getRedPlayer().getPoints() - 3);
            game.getRedPlayer().setGamesPlayed(game.getRedPlayer().getGamesPlayed() + 1);
            game.getRedPlayer().setLoses(game.getRedPlayer().getLoses() + 1);
        } else {
            game.getRedPlayer().setPoints(game.getRedPlayer().getPoints() + 3);
            game.getRedPlayer().setGamesPlayed(game.getRedPlayer().getGamesPlayed() + 1);
            game.getRedPlayer().setWins(game.getRedPlayer().getWins() + 1);
            game.getYellowPlayer().setPoints(game.getYellowPlayer().getPoints() - 3);
            game.getYellowPlayer().setGamesPlayed(game.getYellowPlayer().getGamesPlayed() + 1);
            game.getYellowPlayer().setLoses(game.getYellowPlayer().getLoses() + 1);
        }
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    private GameResponseDTO resultIsDraw(Game game, Integer column) {
        game.setNextMoveNickname("Result is a draw");
        game.setBoardMoves(game.getBoardMoves() + column);
        game.setGameState(GameState.FINISHED);
        game.getYellowPlayer().setGamesPlayed(game.getYellowPlayer().getGamesPlayed() + 1);
        game.getYellowPlayer().setDraws(game.getYellowPlayer().getDraws() + 1);
        game.getRedPlayer().setGamesPlayed(game.getRedPlayer().getGamesPlayed() + 1);
        game.getRedPlayer().setDraws(game.getRedPlayer().getDraws() + 1);
        return mapper.mapToGameResponseDTO(repository.save(game));
    }

    private void checkRules(String nickname, Long id) {
        //TODO: Extract common rules from the other methods
    }
}
