package com.ihu.Connect_4.services;

import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.repositories.AuthenticationDetailsRepository;
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
    private final AuthenticationDetailsRepository authRepository;

    public ScheduledTasks(GameRepository gameRepository, AuthenticationDetailsRepository authRepository) {
        this.gameRepository = gameRepository;
        this.authRepository = authRepository;
    }

    @Scheduled(cron = "0 15 10 * * *", zone = "Europe/Athens")
    @Transactional
    public void clearOldEmptyGames(){
        LocalDateTime now = LocalDateTime.now();

        List<Game> gamesToDelete = gameRepository.findAllByGameStateEqualsOrGameStateEquals(GameState.CREATED, GameState.RUNNING)
                .stream()
                .filter(game -> Math.abs(Duration.between(now, game.getCreatedAt()).toMinutes()) >= 40)
                .collect(Collectors.toList());
        authRepository.deleteOldAuth(gamesToDelete);

        List<Long> idsToDelete = gamesToDelete
                .stream()
                .map(Game::getId)
                .collect(Collectors.toList());
        gameRepository.deleteOldGames(idsToDelete);
    }
}
