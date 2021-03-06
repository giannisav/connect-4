package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(String nickname, boolean isVsAi);
    GameDTO createGameVsAi(String nickname);
    GameDTO joinGame(String nickname, Long id);
    GameDTO play(String nickname, String uuid, Long id, Integer column);
    GameDTO playVsAi(String nickname, String uuid, Long id, Integer column);
    GameDTO getGameStatus(String nickname, String uuid, Long id);
    GameDTO cheatPlay(String nickname, String uuid, Long id);
    List<GameDTO> findAvailableGames();
}
