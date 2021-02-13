package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import org.springframework.data.domain.Slice;

public interface PlayerService {
    PlayerDTO savePlayer(String nickname);
    Slice<PlayerDTO> getPlayerStatistics(SortingType sortingType, SortingOrder sortingOrder, Integer page, Integer size);
}
