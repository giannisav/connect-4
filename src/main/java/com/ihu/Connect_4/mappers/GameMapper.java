package com.ihu.Connect_4.mappers;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.entities.GameDetails;
import com.ihu.Connect_4.entities.Player;
import com.ihu.Connect_4.enums.PlayerColor;
import com.ihu.Connect_4.exceptions.NotExistingPlayerException;
import com.ihu.Connect_4.utils.BoardUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    private static final int YELLOW_PLAYER = 0;
    private static final int RED_PLAYER = 1;

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
        List<Player> players = fetchPlayersByGame(game);
        gameDTO.setId(game.getId());
        gameDTO.setNextMoveNickname(game.getNextMoveNickname());
        gameDTO.setYellowPlayerNickname(getPlayer(game, PlayerColor.YELLOW).getNickname());
        String redNickname = (players.size() == 1) ? "Not connected" : getPlayer(game, PlayerColor.RED).getNickname();
        gameDTO.setRedPlayerNickname(redNickname);
        gameDTO.setBoard(boardUtil.convertTo2DBoard(game.getBoardMoves()));
        gameDTO.setGameState(game.getGameState().name());
        gameDTO.setIsVsAi(game.getIsVsAi());
        return gameDTO;
    }

    private List<Player> fetchPlayersByGame(Game game) {
        return game
                .getGameDetails()
                .stream()
                .map(GameDetails::getPlayer)
                .collect(Collectors.toList());
    }

    private Player getPlayer(Game game, PlayerColor playerColor) {
        return game.getGameDetails().stream()
                .filter(gameDetails -> gameDetails.getPlayerColor() == playerColor)
                .map(GameDetails::getPlayer)
                .findFirst()
                .orElseThrow(() -> new NotExistingPlayerException("Player not found"));
    }
}
