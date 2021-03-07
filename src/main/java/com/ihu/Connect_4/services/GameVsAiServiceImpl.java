package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.mappers.GameMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GameVsAiServiceImpl implements GameVsAiService{

    protected static final String AI_ROBOT = "AI-Robot";

    private final GameService gameService;

    public GameVsAiServiceImpl(GameService gameService, GameMapper mapper) {
        this.gameService = gameService;
    }

    @Transactional
    @Override
    public GameDTO createGameVsAi(String nickname) {
        GameDTO gameDTO = gameService.createGame(nickname, true);
        GameDTO dtoToReturn = gameService.joinGame(AI_ROBOT, gameDTO.getId());
        dtoToReturn.setUuid(gameDTO.getUuid());
        return dtoToReturn;
    }

    @Transactional
    @Override
    public GameDTO playVsAi(String nickname, String uuid, Long id, Integer column) {
        GameDTO gameDTO = gameService.play(nickname, uuid, id, column);
        if (gameDTO.getGameState().equals(GameState.FINISHED.name())){
            return gameDTO;
        }
        Game game = gameService.fetchGame(id);
        GameDTO dtoToReturn = gameService.cheatPlay(AI_ROBOT, game.getAuthenticationDetails().get(1).getUuid(), id);
        dtoToReturn.setUuid(gameDTO.getUuid());
        return dtoToReturn;
    }
}
