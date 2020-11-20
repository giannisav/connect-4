package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;

import java.util.List;

public interface PlayerService {
    PlayerDTO savePlayer(String nickname);
    List<PlayerDTO> getPlayerStatistics(SortingType sortingType, SortingOrder sortingOrder);
}
