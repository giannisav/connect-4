package com.ihu.Connect_4.repositories;

import com.ihu.Connect_4.entities.Game;
import com.ihu.Connect_4.entities.GameDetails;
import com.ihu.Connect_4.entities.GameDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameDetailsRepository extends JpaRepository<GameDetails, GameDetailsId> {

    @Query(value = "select details from GameDetails details where details.id.gameId = ?1 and details.player.nickname = ?2")
    GameDetails findByGameIdAndNickname(Long gameId, String nickname);

    @Modifying
    @Query("delete from GameDetails details WHERE details.game IN (?1)")
    void deleteOldDetails(List<Game> games);

}
