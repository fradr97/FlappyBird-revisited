package com.flappybird.LOGIC;

import com.flappybird.LOGIC.exceptions.InvalidInputException;

public class Player {

    private String nickName;

    private int score;
    private int bestScore;

    public Player(String pNickName) throws InvalidInputException {

        this.setNickName(pNickName);

    }

    public void setNickName(String pNickName) throws InvalidInputException {

        if ((pNickName == null) || (pNickName.trim().equals(""))) {
            throw new InvalidInputException("Invalid nickname");
        }

        this.nickName = pNickName;

    }

    public String getNickname() {
        return this.nickName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getBestScore() {
        return this.bestScore;
    }

}
