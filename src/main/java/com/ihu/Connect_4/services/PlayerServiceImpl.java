package com.ihu.Connect_4.services;

import com.ihu.Connect_4.dtos.PlayerDTO;
import com.ihu.Connect_4.entities.Player;
import com.ihu.Connect_4.enums.SortingOrder;
import com.ihu.Connect_4.enums.SortingType;
import com.ihu.Connect_4.mappers.PlayerMapper;
import com.ihu.Connect_4.repositories.PlayerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository repository;
    private PlayerMapper mapper;

    public PlayerServiceImpl(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlayerDTO savePlayer(String nickname) {
        Player player = Optional.ofNullable(repository.findByNickname(nickname)).orElse(new Player(nickname, 1000L, 0L, 0L, 0L, 0L));
        return mapper.mapToPlayerDTO(repository.save(player));
    }

    @Override
    public List<PlayerDTO> getPlayerStatistics(SortingType sortingType, SortingOrder sortingOrder) {
        if (sortingOrder == null) {
            sortingOrder = SortingOrder.desc;
        }
        if (sortingType == null) {
            sortingType = SortingType.points;
        }
        Sort sort = Sort.by(Sort.Direction.fromString(sortingOrder.getOrder()), sortingType.getField());
        return repository.findAll(sort)
                .stream()
                .map(player -> mapper.mapToPlayerDTO(player))
                .collect(Collectors.toList());
    }
}
