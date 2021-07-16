package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import com.flappybird.connectionDb.ConnectionManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;

public class PlayerSelectionFooter extends JPanel {

    private FlappyBirdFrame flappyBirdFrame;

    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;

    private HotArea startGame;
    private HotArea back;

    public PlayerSelectionFooter(Dimension pDimension, FlappyBirdFrame pFlappyBirdFrame) {

        this.setSize(pDimension.width, pDimension.height / 5);
        this.setLayout(null);

        this.addMouseListener(new IniziaPartitaMouse());

        this.flappyBirdFrame = pFlappyBirdFrame;

        this.startGame = new HotArea();
        this.back = new HotArea();

        this.startGame.symbol = Resources.getImage("/images/PLAY.png");
        this.startGame.width = 300;
        this.startGame.height = 100;
        this.startGame.x = this.getWidth() / 2 + 40;
        this.startGame.y = this.getHeight() - 140;

        this.back.symbol = Resources.getImage("/images/back.png");
        this.back.width = this.startGame.width;
        this.back.height = this.startGame.height - 5;
        this.back.x = this.getWidth() / 2 - 350;
        this.back.y = this.startGame.y;

    }

    @Override
    public void paint(Graphics g) {
        this.startGame.draw(g);
        this.back.draw(g);
    }

    private boolean checkNameInDB(String pName) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();

            String sql = "SELECT * FROM player WHERE nome = '" + pName + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to create the database connection.");
        }
        return false;
    }

    public class IniziaPartitaMouse extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (startGame.isClicked(e)) {
                if (checkNameInDB(flappyBirdFrame.playerSelection.getPlayerNickname())) {
                    defaultFlappyClassic();
                    defaultFlappyShoot();
                    defaultFlappyRun();
                    
                    flappyBirdFrame.switchPanel(flappyBirdFrame.choiceGamesPanel, flappyBirdFrame.playerSelection);
                }
            }

            if (back.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.welcomeScreen, flappyBirdFrame.playerSelection);
            }
        }
    }
    
    private void defaultFlappyClassic(){
        try {
            con = ConnectionManager.getConnection();
            
            String query = "INSERT INTO gioco(id, nome_gioco)"
                + " VALUES (?, ?)";
            
            String verify = "SELECT * FROM gioco WHERE id = 1";
            
            preparedStatement = con.prepareStatement(verify);
            
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                // create the mysql insert preparedstatement
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "FlappyClassic");

                preparedStatement.execute();
                con.close();                
            }
        } catch (SQLException e) {}
    }
    
    private void defaultFlappyShoot(){
        try {
            con = ConnectionManager.getConnection();
            
            String query = "INSERT INTO gioco(id, nome_gioco)"
                + " VALUES (?, ?)";
            
            String verify = "SELECT * FROM gioco WHERE id = 2";
            
            preparedStatement = con.prepareStatement(verify);
            
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                // create the mysql insert preparedstatement
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, "FlappyShoot");

                preparedStatement.execute();
                con.close();                
            }
        } catch (SQLException e) {}
    }
    
    private void defaultFlappyRun(){
        try {
            con = ConnectionManager.getConnection();
            
            String query = "INSERT INTO gioco(id, nome_gioco)"
                + " VALUES (?, ?)";
            
            String verify = "SELECT * FROM gioco WHERE id = 3";
            
            preparedStatement = con.prepareStatement(verify);
            
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                // create the mysql insert preparedstatement
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "FlappyRun");

                preparedStatement.execute();
                con.close();                
            }
        } catch (SQLException e) {}
    }

}
