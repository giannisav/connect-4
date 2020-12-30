package com.ihu.Connect_4.services;

import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.repositories.GameRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    private final GameRepository repository;

    public ScheduledTasks(GameRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Athens")
    public void clearOldEmptyGames(){
        LocalDateTime now = LocalDateTime.now();
        repository.findAllByGameStateEquals(GameState.CREATED)
                .stream()
                .filter(game -> Math.abs(Duration.between(now, game.getCreatedAt()).toMinutes()) > 10)
                .forEach(repository::delete);
    }
}
