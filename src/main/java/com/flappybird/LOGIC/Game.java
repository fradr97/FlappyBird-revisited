package com.flappybird.LOGIC;

import com.flappybird.LOGIC.exceptions.InvalidInputException;

public class Game {

    public Player player;

    public static final int READY_TO_START = 0;
    public static final int ONGOING = 1;
    public static final int END_GAME = 2;

    private int status;

    public Game(Player pPlayer) throws InvalidInputException {

        if (pPlayer == null) {
            throw new InvalidInputException("Invalid Player");
        }

        this.player = pPlayer;
        this.status = READY_TO_START;

    }

    public void start() {
        this.player.setScore(0);
        this.status = ONGOING;
    }

    public void increaseScore() {
        player.setScore(player.getScore() + 1);
    }
    
    public void increaseScore(int add) {           //per ShootBird
        player.setScore(player.getScore() + add);
    }
    
    public void updatesBestScore() {
        player.setBestScore(player.getScore());
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public int getStatus() {

        return this.status;
    }

}
