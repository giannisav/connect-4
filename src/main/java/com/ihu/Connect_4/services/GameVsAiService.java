package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.GameDTO;

public interface GameVsAiService {
    GameDTO createGameVsAi(String nickname);
    GameDTO playVsAi(String nickname, String uuid, Long id, Integer column);
    GameDTO cheatVsAi(String nickname, String uuid, Long id);
}
