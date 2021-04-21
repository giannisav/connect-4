package com.ihu.Connect_4.entities;

import com.ihu.Connect_4.enums.GameState;
import com.ihu.Connect_4.enums.PlayerColor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gameId", unique = true, nullable = false)
    private Long id;

    @Column(length = 42)
    private String nextMoveNickname;

    @OneToMany(mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<GameDetails> gameDetails = new ArrayList<>();


    @Column(length = 42)
    private String boardMoves;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private boolean isVsAi;

    @PrePersist
    public void onPrePersist() {
        if (gameState == null) {
            gameState = GameState.CREATED;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Game() {}

    public Game(Player player, String nextMoveNickname, String boardMoves, boolean isVsAi) {
        this.addPlayer(player);
        this.nextMoveNickname = nextMoveNickname;
        this.boardMoves = boardMoves;
        this.isVsAi = isVsAi;
    }

    public Game(Player player, String uuid, PlayerColor playerColor, String nextMoveNickname, String boardMoves, boolean isVsAi) {
        this.addPlayer(player, uuid, playerColor);
        this.nextMoveNickname = nextMoveNickname;
        this.boardMoves = boardMoves;
        this.isVsAi = isVsAi;
    }

    public Game(Long id, String nextMoveNickname, String boardMoves, GameState gameState, boolean isVsAi) {
        this.id = id;
        this.nextMoveNickname = nextMoveNickname;
        this.boardMoves = boardMoves;
        this.gameState = gameState;
        this.isVsAi = isVsAi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNextMoveNickname() {
        return nextMoveNickname;
    }

    public void setNextMoveNickname(String nextMoveNickname) {
        this.nextMoveNickname = nextMoveNickname;
    }

    public List<GameDetails> getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(List<GameDetails> gameDetails) {
        this.gameDetails = gameDetails;
    }

    public void addPlayer(Player player) {
        GameDetails gameDetails = new GameDetails(this, player);
        this.gameDetails.add(gameDetails);
        player.getGames().add(gameDetails);
    }

    public void addPlayer(Player player, String uuid, PlayerColor playerColor) {
        GameDetails gameDetails = new GameDetails(this, player, uuid, playerColor);
        this.gameDetails.add(gameDetails);
        player.getGames().add(gameDetails);
    }

    public void removePlayer(Player player) {
        for (Iterator<GameDetails> iterator = gameDetails.iterator();
             iterator.hasNext(); ) {
            GameDetails gameDetails = iterator.next();

            if (gameDetails.getGame().equals(this) &&
                    gameDetails.getPlayer().equals(player)) {
                iterator.remove();
                gameDetails.getPlayer().getGames().remove(gameDetails);
                gameDetails.setGame(null);
                gameDetails.setPlayer(null);
            }
        }
    }

    public String getBoardMoves() {
        return boardMoves;
    }

    public void setBoardMoves(String boardMoves) {
        this.boardMoves = boardMoves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean getIsVsAi() {
        return isVsAi;
    }

    public void setIsVsAi(boolean vsAi) {
        isVsAi = vsAi;
    }
}
