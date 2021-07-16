package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import com.flappybird.connectionDb.ConnectionManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;

public class EndPanelShootBirds extends JPanel{
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    private FlappyBirdFrame flappyBirdFrame;
    
    private HotArea newMatch;
    private HotArea exit;
    private HotArea home;

    private Image gameOver;
    private Image die;

    private Point gameOverPosition;
    private Dimension gameOverSize;

    private Point diePosition;
    private Dimension dieSize;
    
    
    public EndPanelShootBirds(FlappyBirdFrame pFlappyBirdFrame) {
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.flappyBirdFrame = pFlappyBirdFrame;
        
        this.addMouseListener(new EndMouseListener());
        
        this.newMatch = new HotArea();
        this.exit = new HotArea();
        this.home = new HotArea();


        this.gameOver = Resources.getImage("/images/game_over.png");
        this.gameOverSize = new Dimension(this.getWidth() / 2, 300);
        this.gameOverPosition = new Point(this.getHeight() / 4, -50);

        this.die = Resources.getImage("/images/CRASH.png");
        this.dieSize = new Dimension(300, 700);
        this.diePosition = new Point(this.getWidth() / 2 + 140, this.getHeight() / 8 - 180);

        this.newMatch.symbol = Resources.getImage("/images/new_match.png");
        this.newMatch.width = 250;
        this.newMatch.height = 75;
        this.newMatch.x = 275;
        this.newMatch.y = this.getHeight() / 2 + 120;
        
        this.home.symbol = Resources.getImage("/images/home.png");
        this.home.width = 100;
        this.home.height = 100;
        this.home.x = 50;
        this.home.y = 600;

        this.exit.symbol = Resources.getImage("/images/EXIT2.png");
        this.exit.width = 190;
        this.exit.height = 65;
        this.exit.x = 300;
        this.exit.y = newMatch.y + newMatch.height + 10;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(gameOver, gameOverPosition.x, gameOverPosition.y, gameOverSize.width, gameOverSize.height, null);
        g.drawImage(die, diePosition.x, diePosition.y, dieSize.width, dieSize.height, null);

        g.setColor(Color.RED);
        g.setFont(new Font("Harrington", 1, 60));

        g.drawString(flappyBirdFrame.playerSelection.content.getPlayerNickName() + " ", 25, this.getHeight() / 2 - 150);

        g.setColor(Color.white);

        g.drawString("SCORE : ", 100, this.getHeight() / 2 - 50);
        g.drawString("BEST SCORE : ", 25, this.getHeight() / 2 + 30);

        g.drawString(flappyBirdFrame.gamePanelShootBirds.game.player.getScore() + "", this.getWidth() / 2 - 25, this.getHeight() / 2 - 50);

        g.drawString(getBestScore(flappyBirdFrame.gamePanelShootBirds.game.player.getNickname()) + "", this.getWidth() / 2 + 75, this.getHeight() / 2 + 30);

        this.newMatch.draw(g);
        this.home.draw(g);
        this.exit.draw(g);

    }
    
    private class EndMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (newMatch.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.gamePanelShootBirds, flappyBirdFrame.endPanelShootBirds);
                flappyBirdFrame.gamePanelShootBirds.resetGame();
            }
            if (home.isClicked(e)) {

                flappyBirdFrame.switchPanel(flappyBirdFrame.choiceGamesPanel, flappyBirdFrame.endPanelShootBirds);
                flappyBirdFrame.gamePanelShootBirds.resetGame();
            }

            if (exit.isClicked(e)) {
                exit(0);
            }
        }
    }
    
    private int getIdPlayerFromDb(String nickname) throws SQLException {
        int result = 0;
                
        try {
            con = ConnectionManager.getConnection();
            
            String sql = "SELECT id FROM player WHERE nome = '" + nickname + "'";
            
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                result = rs.getInt("id");
                System.out.println("result: " + result);
            }
            else
                result = 0;
            
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed!");
        }
        
        return result;
    }
    
    
    private int getBestScore(String name){
        int bestScore = 0;
        
        try{            
            int id = getIdPlayerFromDb(name);
            
            con = ConnectionManager.getConnection();

            String sql = "SELECT max(punteggio) FROM punteggio WHERE id_player = " + id + " AND id_gioco = 2";

            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                bestScore = rs.getInt("MAX(punteggio)");
            }
            else
                bestScore = 0;

            con.close();
        }catch(SQLException e){
            System.out.println("Failed!");
        }
        
        return bestScore;
    }
    
}
