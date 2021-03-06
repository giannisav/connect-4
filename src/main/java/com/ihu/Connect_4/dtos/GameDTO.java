package com.ihu.Connect_4.dtos;

public class GameDTO {

    private Long id;
    private String nextMoveNickname;
    private String yellowPlayerNickname;
    private String redPlayerNickname;
    private String uuid;
    private char[][] board;
    private String gameState;
    private boolean isVsAi;

    public GameDTO() {}

    public GameDTO(Long id, String nextMoveNickname, String yellowPlayerNickname, String redPlayerNickname, String uuid, char[][] board, String gameState, boolean isVsAi) {
        this.id = id;
        this.nextMoveNickname = nextMoveNickname;
        this.yellowPlayerNickname = yellowPlayerNickname;
        this.redPlayerNickname = redPlayerNickname;
        this.uuid = uuid;
        this.board = board;
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

    public String getYellowPlayerNickname() { return yellowPlayerNickname; }

    public void setYellowPlayerNickname(String yellowPlayerNickname) { this.yellowPlayerNickname = yellowPlayerNickname; }

    public String getRedPlayerNickname() { return redPlayerNickname; }

    public void setRedPlayerNickname(String redPlayerNickname) { this.redPlayerNickname = redPlayerNickname; }

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

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

    public boolean getIsVsAi() {
        return isVsAi;
    }

    public void setIsVsAi(boolean isVsAi) {
        this.isVsAi = isVsAi;
    }
}
