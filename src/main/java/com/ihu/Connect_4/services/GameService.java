package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameResponseDTO;
import com.ihu.Connect_4.entities.Game;

public interface GameService {
    GameResponseDTO createGame(String nickname);
    GameResponseDTO joinGame(String nickname, Long id);
    GameResponseDTO play(String nickname, String token, Long id, int column);
    GameResponseDTO getGameStatus(String nickname, Long id);
    GameResponseDTO cheatPlay(String nickname, String token, Long id);
}
