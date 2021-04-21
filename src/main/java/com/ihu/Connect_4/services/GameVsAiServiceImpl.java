package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.GameDetails;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.repositories.GameDetailsRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GameVsAiServiceImpl implements GameVsAiService{

    protected static final String AI_ROBOT = "AI-Robot";

    private final GameService gameService;
    private final GameDetailsRepository gameDetailsRepository;

    public GameVsAiServiceImpl(GameService gameService, GameDetailsRepository gameDetailsRepository) {
        this.gameService = gameService;
        this.gameDetailsRepository = gameDetailsRepository;
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
        return basicPlayVsAi(id, gameDTO);
    }

    @Transactional
    @Override
    public GameDTO cheatVsAi(String nickname, String uuid, Long id) {
        GameDTO gameDTO = gameService.cheatPlay(nickname, uuid, id);
        return basicPlayVsAi(id, gameDTO);
    }

    private GameDTO basicPlayVsAi(Long id, GameDTO gameDTO) {
        if (gameDTO.getGameState().equals(GameState.FINISHED.name())){
            return gameDTO;
        }
        GameDetails gameDetails = gameDetailsRepository.findByGameIdAndNickname(id, AI_ROBOT);
        GameDTO dtoToReturn = gameService.cheatPlay(AI_ROBOT, gameDetails.getUuid(), id);
        dtoToReturn.setUuid(gameDTO.getUuid());
        return dtoToReturn;
    }
}
