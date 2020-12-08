package com.ihu.Connect_4.dtos;

public class GameResponseDTO {

    private Long id;
    private String nextMoveNickname;
    private String yellowPlayerNickname;
    private String yellowToken;
    private String redPlayerNickname;
    private String redToken;
    private char[][] board;
    private String gameState;

    public GameResponseDTO() {}

    public GameResponseDTO(Long id, String nextMoveNickname, String yellowPlayerNickname, String yellowToken, String redPlayerNickname, String redToken, char[][] board, String gameState) {
        this.id = id;
        this.nextMoveNickname = nextMoveNickname;
        this.yellowPlayerNickname = yellowPlayerNickname;
        this.yellowToken = yellowToken;
        this.redPlayerNickname = redPlayerNickname;
        this.redToken = redToken;
        this.board = board;
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

    public String getYellowPlayerNickname() { return yellowPlayerNickname; }

    public void setYellowPlayerNickname(String yellowPlayerNickname) { this.yellowPlayerNickname = yellowPlayerNickname; }

    public String getYellowToken() { return yellowToken; }

    public void setYellowToken(String yellowToken) { this.yellowToken = yellowToken; }

    public String getRedPlayerNickname() { return redPlayerNickname; }

    public void setRedPlayerNickname(String redPlayerNickname) { this.redPlayerNickname = redPlayerNickname; }

    public String getRedToken() { return redToken; }

    public void setRedToken(String redToken) { this.redToken = redToken; }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
