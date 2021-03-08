package com.ihu.Connect_4.repositories;

import com.ihu.Connect_4.entities.AuthenticationDetails;
import com.ihu.Connect_4.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticationDetailsRepository extends JpaRepository <AuthenticationDetails, Long> {

    Optional<AuthenticationDetails> findByNicknameAndUuidAndGameEquals(String nickname, String uuid, Game game);

    @Modifying
    @Query("delete from AuthenticationDetails auth WHERE auth.game IN (?1)")
    void deleteOldAuth(List<Game> games);
}
