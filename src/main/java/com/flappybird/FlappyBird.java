package com.flappybird;

import com.flappybird.GUI.FlappyBirdFrame;
import java.sql.SQLException;

public class FlappyBird {

    public static void main(String[] args) throws SQLException {
       FlappyBirdFrame flappyBirdFrame = new FlappyBirdFrame();
       flappyBirdFrame.setVisible(true);
    }
}
