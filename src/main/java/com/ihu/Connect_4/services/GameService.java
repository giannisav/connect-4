package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;
import com.ihu.Connect_4.entities.Game;

import java.util.List;

public interface GameService {

    GameDTO createGame(String nickname, boolean isVsAi);
    GameDTO joinGame(String nickname, Long id);
    GameDTO play(String nickname, String uuid, Long id, Integer column);
    GameDTO getGameStatus(String nickname, String uuid, Long id);
    GameDTO cheatPlay(String nickname, String uuid, Long id);
    List<GameDTO> findAvailableGames();
    Game fetchGame(Long id);
    Boolean needsUpdate(Long id, int numOfMoves);
}
