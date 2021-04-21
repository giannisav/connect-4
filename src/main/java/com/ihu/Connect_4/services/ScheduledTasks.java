package com.ihu.Connect_4.services;

import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.repositories.GameDetailsRepository;
import com.ihu.Connect_4.repositories.GameRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    private final GameRepository gameRepository;
    private final GameDetailsRepository gameDetailsRepository;

    public ScheduledTasks(GameRepository gameRepository, GameDetailsRepository gameDetailsRepository) {
        this.gameRepository = gameRepository;
        this.gameDetailsRepository = gameDetailsRepository;
    }

    @Scheduled(cron = "0 15 10 * * *", zone = "Europe/Athens")
    @Transactional
    public void clearOldEmptyGames(){
        LocalDateTime now = LocalDateTime.now();

        List<Game> gamesToDelete = gameRepository.findAllByGameStateEqualsOrGameStateEquals(GameState.CREATED, GameState.RUNNING)
                .stream()
                .filter(game -> Math.abs(Duration.between(now, game.getCreatedAt()).toMinutes()) >= 40)
                .collect(Collectors.toList());
        gameDetailsRepository.deleteOldDetails(gamesToDelete);

        List<Long> idOfGamesToDelete = gamesToDelete.stream()
                .map(Game::getId)
                .collect(Collectors.toList());

        gameRepository.deleteOldGames(idOfGamesToDelete);
    }
}
