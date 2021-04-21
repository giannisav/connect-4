package com.ihu.Connect_4.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playerId")
    private Long id;

    @Column(name = "nickname", length = 42, unique = true, nullable = false)
    private String nickname;

    @OneToMany(
            mappedBy = "player",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<GameDetails> games = new ArrayList<>();

    private Long points ;

    private Long gamesPlayed ;

    private Long wins;

    private Long loses;

    private Long draws;

    private Long winsVsAi;

    public Player (){}

    public Player(String nickname, Long points, Long gamesPlayed, Long wins, Long loses, Long draws, Long winsVsAi) {
        this.nickname = nickname;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
        this.winsVsAi = winsVsAi;
    }

    public Player(Long id, String nickname, Long points, Long gamesPlayed, Long wins, Long loses, Long draws, Long winsVsAi) {
        this.id = id;
        this.nickname = nickname;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
        this.winsVsAi = winsVsAi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<GameDetails> getGames() {
        return games;
    }

    public void setGames(List<GameDetails> games) {
        this.games = games;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Long getLoses() {
        return loses;
    }

    public void setLoses(Long loses) {
        this.loses = loses;
    }

    public Long getDraws() {
        return draws;
    }

    public void setDraws(Long draws) {
        this.draws = draws;
    }

    public Long getWinsVsAi() {
        return winsVsAi;
    }

    public void setWinsVsAi(Long winsVsAi) {
        this.winsVsAi = winsVsAi;
    }
}
