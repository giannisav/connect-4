package com.ihu.Connect_4.dtos;

public class GameResponseDTO {

    private Long id;
    private String nextMoveNickname;
    private String yellowPlayerNickname;
    private String yellowUuid;
    private String redPlayerNickname;
    private String redUuid;
    private char[][] board;
    private String gameState;

    public GameResponseDTO() {}

    public GameResponseDTO(Long id, String nextMoveNickname, String yellowPlayerNickname, String yellowUuid, String redPlayerNickname, String redUuid, char[][] board, String gameState) {
        this.id = id;
        this.nextMoveNickname = nextMoveNickname;
        this.yellowPlayerNickname = yellowPlayerNickname;
        this.yellowUuid = yellowUuid;
        this.redPlayerNickname = redPlayerNickname;
        this.redUuid = redUuid;
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

    public String getYellowUuid() { return yellowUuid; }

    public void setYellowUuid(String yellowUuid) { this.yellowUuid = yellowUuid; }

    public String getRedPlayerNickname() { return redPlayerNickname; }

    public void setRedPlayerNickname(String redPlayerNickname) { this.redPlayerNickname = redPlayerNickname; }

    public String getRedUuid() { return redUuid; }

    public void setRedUuid(String redUuid) { this.redUuid = redUuid; }

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
