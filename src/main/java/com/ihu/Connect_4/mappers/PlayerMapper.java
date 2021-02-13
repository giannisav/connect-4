package com.ihu.Connect_4.mappers;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.entities.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapper {

    public PlayerDTO mapToPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setNickname(player.getNickname());
        playerDTO.setPoints(player.getPoints());
        playerDTO.setGamesPlayed(player.getGamesPlayed());
        playerDTO.setWins(player.getWins());
        playerDTO.setLoses(player.getLoses());
        playerDTO.setDraws(player.getDraws());
        return playerDTO;
    }

    public List<PlayerDTO> mapToPlayerDTOList(List<Player> playerList) {
        return playerList
                .stream()
                .map(this::mapToPlayerDTO)
                .collect(Collectors.toList());
    }
}
