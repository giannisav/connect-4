package com.ihu.Connect_4.repositories;

import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.enums.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByGameStateEquals(GameState state);

    @Modifying
    @Query("delete from Game g WHERE g.id IN (?1)")
    void deleteOldGames(List<Long> ids);

}

