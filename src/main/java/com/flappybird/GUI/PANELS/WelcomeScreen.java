package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.System.exit;
import javax.swing.JPanel;

public class WelcomeScreen extends JPanel {

    private FlappyBirdFrame flappyBirdFrame;

    private Image logo;

    private HotArea play;
    private HotArea exit;

    public WelcomeScreen(FlappyBirdFrame pFlappyBirdFrame) {

        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.flappyBirdFrame = pFlappyBirdFrame;

        this.addMouseListener(new WelcomeMouseListener());

        this.play = new HotArea();
        this.exit = new HotArea();

        this.logo = Resources.getImage("/images/LOGO.jpg");

        this.play.symbol = Resources.getImage("/images/PLAY.png");
        this.play.width = 300;
        this.play.height = 100;
        this.play.x = (this.getWidth() - this.play.width) / 2;
        this.play.y = this.getHeight() / 2;

        this.exit.symbol = Resources.getImage("/images/EXIT2.png");
        this.exit.width =190;
        this.exit.height = 65;
        this.exit.x = play.x + 50;
        this.exit.y = play.y + play.height + 20;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.drawImage(logo, 0, 0, this.getWidth(), this.getHeight(), null);

        this.play.draw(g);
        this.exit.draw(g);
    }

    private class WelcomeMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (play.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.playerSelection, flappyBirdFrame.welcomeScreen);
            }
            if (exit.isClicked(e)) {
                exit(0);
            }
        }

    }

}
