package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerSelectionContent extends JPanel {

    private FlappyBirdFrame flappyBirdFrame;

    private JTextField fieldPlayer;

    private HotArea signIn;
    private HotArea score;

    public PlayerSelectionContent(Dimension pDimension, FlappyBirdFrame pFlappyBirdFrame) {
        this.setSize(pDimension.width, pDimension.height * 3 / 5);
        this.setLayout(null);
        this.addMouseListener(new MouseRegistrationListener());
        this.flappyBirdFrame = pFlappyBirdFrame;

        JLabel player;
        Image playerImage;

        this.fieldPlayer = new JTextField("Enter your nickname or register");
        this.fieldPlayer.setFont(new Font("Harrington", 1, 30));
        this.fieldPlayer.setBounds(this.getWidth() / 4 - 50, this.getHeight() / 4, (int) (this.getWidth() / 1.5), this.getHeight() / 6);
        this.add(fieldPlayer);

        playerImage = Resources.getImage("/images/BIRD.png");
        playerImage = playerImage.getScaledInstance(fieldPlayer.getHeight() - 15, fieldPlayer.getHeight() - 15, 0);

        player = new JLabel("");
        player.setBounds((fieldPlayer.getX() - playerImage.getWidth(null)) / 2, fieldPlayer.getY(), fieldPlayer.getWidth(), fieldPlayer.getHeight());
        player.setIcon(new ImageIcon(playerImage));
        this.add(player);
        
        this.signIn = new HotArea();
        this.score = new HotArea();
        
        this.signIn.symbol = Resources.getImage("/images/signIn.png");
        this.signIn.width = 400;
        this.signIn.height = 80;
        this.signIn.x = (this.getWidth() - 350) / 2 -10;
        this.signIn.y = fieldPlayer.getY() + 100;
        
        this.score.symbol = Resources.getImage("/images/cup.png");
        this.score.width = 100;
        this.score.height = 100;
        this.score.x = (this.getWidth() - 350) / 2 +140;
        this.score.y = fieldPlayer.getY() + 210;
           
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color color = new Color(130, 192, 255);
        g.setColor(color);
        g.fillRect(0, 0, FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        
        this.signIn.draw(g);
        this.score.draw(g);
    }

    public String getPlayerNickName() {
        return fieldPlayer.getText();
    }

    private class MouseRegistrationListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (signIn.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.registrationPanel, flappyBirdFrame.playerSelection);
            }
            
            if(score.isClicked(e)){
                flappyBirdFrame.switchPanel(flappyBirdFrame.scorePanel, flappyBirdFrame.playerSelection);
            }
        }
    }
}
