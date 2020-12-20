package com.ihu.Connect_4.mappers;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.utils.BoardUtil;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    private BoardUtil boardUtil;

    public GameMapper(BoardUtil boardUtil) {
        this.boardUtil = boardUtil;
    }

    public GameResponseDTO mapToGameResponseDTO(Game game) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(game.getId());
        gameResponseDTO.setNextMoveNickname(game.getNextMoveNickname());
        gameResponseDTO.setYellowPlayerNickname(game.getYellowPlayer().getNickname());
        gameResponseDTO.setYellowUuid(game.getYellowPlayer().getUuid());
        String redNickname = null == game.getRedPlayer() ? "Not connected" : game.getRedPlayer().getNickname();
        gameResponseDTO.setRedPlayerNickname(redNickname);
        String redUuid = redNickname.equals("Not connected") ? "Empty" : game.getRedPlayer().getUuid();
        gameResponseDTO.setRedUuid(redUuid);
        gameResponseDTO.setBoard(boardUtil.convertTo2DBoard(game.getBoardMoves()));
        gameResponseDTO.setGameState(game.getGameState().name());
        return gameResponseDTO;
    }
}
