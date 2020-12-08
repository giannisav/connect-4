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
        gameResponseDTO.setYellowToken(game.getYellowPlayer().getFakeToken());
        String redNickname = null == game.getRedPlayer() ? "Not connected" : game.getRedPlayer().getNickname();
        gameResponseDTO.setRedPlayerNickname(redNickname);
        String redToken = redNickname.equals("Not connected") ? "Empty" : game.getRedPlayer().getFakeToken();
        gameResponseDTO.setRedToken(redToken);
        gameResponseDTO.setBoard(boardUtil.convertTo2DBoard(game.getBoardMoves()));
        gameResponseDTO.setGameState(game.getGameState().name());
        return gameResponseDTO;
    }
}
