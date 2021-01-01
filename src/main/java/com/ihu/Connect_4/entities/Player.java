package com.ihu.Connect_4.entities;


import javax.persistence.*;


@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    private Long points ;

    private Long gamesPlayed ;

    private Long wins;

    private Long loses;

    private Long draws;

    @Column(name = "uuid", unique = true)
    private String uuid;

    public Player (){}

    public Player(String nickname, Long points, Long gamesPlayed, Long wins, Long loses, Long draws) {
        this.nickname = nickname;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
    }

    public Player(Long id, String nickname, Long points, Long gamesPlayed, Long wins, Long loses, Long draws, String uuid) {
        this.id = id;
        this.nickname = nickname;
        this.points = points;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
        this.uuid = uuid;
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

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }
}
