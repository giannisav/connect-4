package com.ihu.Connect_4.repositories;

import com.ihu.Connect_4.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByNickname(String nickname);
}
