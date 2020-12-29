package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.entities.Game;

import java.util.List;

public interface GameService {

    GameResponseDTO createGame(String nickname);
    GameResponseDTO joinGame(String nickname, Long id);
    GameResponseDTO play(String nickname, String uuid, Long id, Integer column);
    GameResponseDTO getGameStatus(String nickname, Long id);
    GameResponseDTO cheatPlay(String nickname, String uuid, Long id);
    List<GameResponseDTO> findAvailableGames();

}
