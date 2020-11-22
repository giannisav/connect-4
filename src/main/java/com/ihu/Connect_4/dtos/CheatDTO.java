package com.ihu.Connect_4.dtos;

import org.springframework.stereotype.Component;

import java.util.List;

public class CheatDTO {

    String pos;
    List<Integer> score;

    public CheatDTO() {}

    public CheatDTO(String pos, List<Integer> score) {
        this.pos = pos;
        this.score = score;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GameCheatDTO{" +
                "pos='" + pos + '\'' +
                ", score=" + score +
                '}';
    }
}
