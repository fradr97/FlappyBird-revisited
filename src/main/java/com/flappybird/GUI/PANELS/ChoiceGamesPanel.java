package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.LOGIC.Player;
import com.flappybird.LOGIC.exceptions.InvalidInputException;
import com.flappybird.UTILS.Resources;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class ChoiceGamesPanel extends JPanel{
    private FlappyBirdFrame flappyBirdFrame;
    
    private HotArea gioco1;
    private HotArea gioco2;
    private HotArea gioco3;
    private HotArea back;
    
    private Image logo;
    
    public ChoiceGamesPanel(FlappyBirdFrame flappyBirdFrame){
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.flappyBirdFrame = flappyBirdFrame;
        this.addMouseListener(new MouseChoice());
        
        this.gioco1 = new HotArea();
        this.gioco1.symbol = Resources.getImage("/images/classic.png");
        this.gioco1.width = 350;
        this.gioco1.height = 80;
        this.gioco1.x = (this.getWidth() / 2) - 370; 
        this.gioco1.y = this.getHeight() /2 - 100;
        
        this.gioco2 = new HotArea();
        this.gioco2.symbol = Resources.getImage("/images/shoot.png");
        this.gioco2.width = gioco1.width;
        this.gioco2.height = gioco1.height;
        this.gioco2.x = (this.getWidth() / 2); 
        this.gioco2.y = this.gioco1.y;
        
        this.gioco3 = new HotArea();
        this.gioco3.symbol = Resources.getImage("/images/runVersion.png");
        this.gioco3.width = gioco1.width;
        this.gioco3.height = gioco1.height;
        this.gioco3.x = (this.getWidth() -380) / 2; 
        this.gioco3.y = this.gioco1.y + 100;
         
        this.back = new HotArea();
        this.back.symbol = Resources.getImage("/images/back.png");
        this.back.width = 280;
        this.back.height = 90;
        this.back.x = this.getWidth() / 2 - 350;
        this.back.y = this.gioco3.y + 250; 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
       Color color = new Color(130, 192, 255);
       g.setColor(color);
       
       g.fillRect(0, 0, this.getWidth(), this.getHeight());
       
       this.gioco1.draw(g);
       this.gioco2.draw(g);
       this.gioco3.draw(g);
       this.back.draw(g);
       
       Color colorWrite = new Color(255, 066, 030);

        g.setColor(colorWrite);
        g.fillRoundRect(20, 20, 760, 100, 80, 80);

        g.setFont(new Font("Harrington", 1, 75));
        g.setColor(Color.white);

        g.drawString("SELECT GAME", 95, 100);
    }
    
    private class MouseChoice extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (gioco1.isClicked(e)) {
                try {
                    Player player = new Player(flappyBirdFrame.playerSelection.getPlayerNickname());
                    flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelFlappyClassic, flappyBirdFrame.choiceGamesPanel);
                    flappyBirdFrame.gamePanelFlappyClassic.startGame(player);
                } catch (InvalidInputException ex) {
                    Logger.getLogger(ChoiceGamesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (gioco2.isClicked(e)) {
                try {
                    Player player = new Player(flappyBirdFrame.playerSelection.getPlayerNickname());
                    flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelShootBirds, flappyBirdFrame.choiceGamesPanel);
                    flappyBirdFrame.gamePanelShootBirds.startGame(player);
                } catch (InvalidInputException ex) {
                    Logger.getLogger(ChoiceGamesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (gioco3.isClicked(e)) {
                try {
                   Player player = new Player(flappyBirdFrame.playerSelection.getPlayerNickname());
                    flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelShootRunBirds, flappyBirdFrame.choiceGamesPanel);
                    flappyBirdFrame.gamePanelShootRunBirds.startGame(player);
                } catch (InvalidInputException ex) {
                    Logger.getLogger(ChoiceGamesPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(back.isClicked(e)){
                flappyBirdFrame.switchPanel(flappyBirdFrame.playerSelection, flappyBirdFrame.choiceGamesPanel);
            }
        }
    }
    
}
