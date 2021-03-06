package com.ihu.Connect_4.mappers;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.utils.BoardUtil;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    private final BoardUtil boardUtil;

    public GameMapper(BoardUtil boardUtil) {
        this.boardUtil = boardUtil;
    }

    public GameDTO mapToGameDTO(Game game) {
        GameDTO gameDTO = baseMapping(game);
        gameDTO.setUuid("");
        return gameDTO;
    }

    public GameDTO mapToGameDTOWithUuid(Game game, String uuid) {
        GameDTO gameDTO = baseMapping(game);
        gameDTO.setUuid(uuid);
        return gameDTO;
    }

    private GameDTO baseMapping(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setNextMoveNickname(game.getNextMoveNickname());
        gameDTO.setYellowPlayerNickname(game.getYellowPlayer().getNickname());
        String redNickname = null == game.getRedPlayer() ? "Not connected" : game.getRedPlayer().getNickname();
        gameDTO.setRedPlayerNickname(redNickname);
        gameDTO.setBoard(boardUtil.convertTo2DBoard(game.getBoardMoves()));
        gameDTO.setGameState(game.getGameState().name());
        gameDTO.setIsVsAi(game.getIsVsAi());
        return gameDTO;
    }
}
