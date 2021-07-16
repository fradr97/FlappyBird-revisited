package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import com.flappybird.UTILS.Sound;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class StatusBarPanelShootingBirds extends JPanel{
    private FlappyBirdFrame flappyBirdFrame;

    public HotArea pause;
    private HotArea music;

    public int seconds;
    private boolean running;

    private Sound melody;

    public StatusBarPanelShootingBirds(FlappyBirdFrame pFlappyBirdFrame) {
        this.addMouseListener(new StatusPanelListener());

        this.flappyBirdFrame = pFlappyBirdFrame;

        this.pause = new HotArea();
        this.music = new HotArea();

        this.melody = Resources.getSound("/sound/Melodia.wav");

        this.pause.symbol = Resources.getImage("/images/PAUSE_B.png");
        this.pause.width = 30;
        this.pause.height = 30;
        this.pause.x = 420;
        this.pause.y = 7;
        this.pause.isActive(false);   //finchè non parto il pulsante pause è disabilitato

        this.music.symbol = Resources.getImage("/images/musica.png");
        this.music.width = pause.width;
        this.music.height = pause.height;
        this.music.x = 10;
        this.music.y = pause.y;

        this.seconds = 0;

    }

    public void startTimer() {
        this.running = true;

        Thread timer = new Thread(new TimerThread());
        timer.start();
    }

    public void stopTimer() {
        this.running = false;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void aggiornaSeconds() {
        this.setSeconds(this.getSeconds() + 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color color = new Color(130, 192, 255);
        g.setColor(color);
        g.fillRect(0, 0, FlappyBirdFrame.WIDTH_FRAME, 40);

        this.pause.draw(g);
        this.music.draw(g);

        g.setColor(Color.white);
        g.setFont(new Font("Harrington", 1, 35));
        g.drawString(this.seconds + "", this.getWidth() / 2 - 20, 30);

    }

    private class StatusPanelListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (pause.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.stopPanelShootBirds, flappyBirdFrame.gamePanelShootBirds);
                flappyBirdFrame.gamePanelShootBirds.stopTimer();
                flappyBirdFrame.gamePanelShootBirds.stopFlight();
            }
            if (music.isClicked(e)) {
                melody.play(100);
                music.isActive(false);
            }
        }

    }

    private class TimerThread implements Runnable {

        @Override
        public void run() {
            while (running) {
                aggiornaSeconds();
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StatusBarPanelShootingBirds.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}

