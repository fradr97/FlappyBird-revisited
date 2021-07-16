package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.System.exit;
import javax.swing.JPanel;

public class StopPanelShootBirds extends JPanel{
    private HotArea newMatch;
    private HotArea resumeGame;
    private HotArea exit;

    private Image background;
    private Image logo;

    private FlappyBirdFrame flappyBirdFrame;

    public StopPanelShootBirds(FlappyBirdFrame pFlappyBirdFrame) {
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.addMouseListener(new StopMouseListener());

        this.flappyBirdFrame = pFlappyBirdFrame;

        this.newMatch = new HotArea();
        this.resumeGame = new HotArea();
        this.exit = new HotArea();

        this.background = Resources.getImage("/images/sfondo.jpg");
        this.logo = Resources.getImage("/images/FlappyBird.png");

        this.newMatch.symbol = Resources.getImage("/images/new_match.png");
        this.newMatch.width = 250;
        this.newMatch.height = 65;
        this.newMatch.x = 60;
        this.newMatch.y = 650;

        this.resumeGame.symbol = Resources.getImage("/images/resume.png");
        this.resumeGame.width = 180;
        this.resumeGame.height = 150;
        this.resumeGame.x = 350;
        this.resumeGame.y = 400;

        this.exit.symbol = Resources.getImage("/images/EXIT2.png");
        this.exit.width = 190;
        this.exit.height = 65;
        this.exit.x = newMatch.x + 450;
        this.exit.y = newMatch.y;


    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(logo, 200, 80, this.getWidth() / 2, this.getHeight() - 600, null);

        g.setColor(Color.white);
        g.setFont(new Font("Harrington", 1, 60));

        g.drawString("SCORE : ", 100, this.getHeight() / 2 - 50);
        g.drawString(flappyBirdFrame.gamePanelShootBirds.game.player.getScore() + "", this.getWidth() / 2 - 25, this.getHeight() / 2 - 50);

        this.newMatch.draw(g);
        this.resumeGame.draw(g);
        this.exit.draw(g);

    }

    private class StopMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (newMatch.isClicked(e)) {
                flappyBirdFrame.gamePanelShootBirds.statusBarPanelShootingBirds.pause.isActive(false);
                flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelShootBirds, flappyBirdFrame.stopPanelShootBirds);
                flappyBirdFrame.gamePanelShootBirds.resetGame();
            }
            if (resumeGame.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelShootBirds, flappyBirdFrame.stopPanelShootBirds);
                flappyBirdFrame.gamePanelShootBirds.startTimer();
                flappyBirdFrame.gamePanelShootBirds.startFlightBird();
                flappyBirdFrame.gamePanelShootBirds.startFlightBirdBonus();
                flappyBirdFrame.gamePanelShootBirds.startFlightBirdEvil();
                flappyBirdFrame.gamePanelShootBirds.startFlightBirdLeft();
                flappyBirdFrame.gamePanelShootBirds.startFlightBirdMiniEvil();
            }
            if (exit.isClicked(e)) {
                exit(0);
            }
        }

    }

}
