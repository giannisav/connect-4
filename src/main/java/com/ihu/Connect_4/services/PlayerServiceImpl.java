package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.entities.Player;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import com.ihu.Connect_4.exceptions.XssException;
import com.ihu.Connect_4.mappers.PlayerMapper;
import com.ihu.Connect_4.repositories.PlayerRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    public PlayerServiceImpl(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlayerDTO savePlayer(String nickname) {
        avoidXSS(nickname);
        Player player = repository.findByNickname(nickname)
                .orElse(new Player(nickname, 1000L, 0L, 0L, 0L, 0L, 0L));
        return mapper.mapToPlayerDTO(repository.save(player));
    }

    @Override
    public Slice<PlayerDTO> getPlayerStatistics(SortingType sortingType, SortingOrder sortingOrder, Integer page, Integer size) {
        sortingType = Optional.ofNullable(sortingType).orElse(SortingType.points);
        sortingOrder = Optional.ofNullable(sortingOrder).orElse(SortingOrder.desc);
        page = Optional.ofNullable(page).orElse(0);
        size = Optional.ofNullable(size).orElse(10);

        Sort sort = Sort.by(Sort.Direction.fromString(sortingOrder.getOrder()), sortingType.getField());
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Player> statisticSlice = repository.findAll(pageable);
        return new SliceImpl<>(mapper.mapToPlayerDTOList(statisticSlice.getContent()), pageable, statisticSlice.hasNext());
    }

    private void avoidXSS(String input) {
        if (input != null && !input.equals(HtmlUtils.htmlEscape(input))) {
            throw new XssException("Not allowed nickname.");
        }
    }
}
