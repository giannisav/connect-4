package com.ihu.Connect_4.entities;

import com.ihu.Connect_4.enums.GameState;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nextMoveNickname;

    @OneToOne(cascade = CascadeType.ALL)
    private Player yellowPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    private Player redPlayer;

    @Column(length = 42)
    private String boardMoves;

    @Enumerated(EnumType.STRING)
    private GameState gameState ;

    @PrePersist
    public void onPrePersist() {
        if (gameState == null) {
            gameState = GameState.CREATED;
        }
    }

    public Game(){}

    public Game(Long id, String nextMoveNickname, Player yellowPlayer, Player redPlayer, String boardMoves, GameState gameState) {
        this.id = id;
        this.nextMoveNickname = nextMoveNickname;
        this.yellowPlayer = yellowPlayer;
        this.redPlayer = redPlayer;
        this.boardMoves = boardMoves;
        this.gameState = gameState;
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

    public Player getYellowPlayer() {
        return yellowPlayer;
    }

    public void setYellowPlayer(Player yellowPlayer) {
        this.yellowPlayer = yellowPlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
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
}
