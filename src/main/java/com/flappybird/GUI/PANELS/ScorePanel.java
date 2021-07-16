package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.UTILS.Resources;
import com.flappybird.connectionDb.ConnectionManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScorePanel extends JPanel{
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs;
    
    private FlappyBirdFrame flappyBirdFrame;
    
    private JScrollPane jScrollPane;
    
    private ArrayList<String> listNamePlayer = new ArrayList<>();
    private ArrayList<String> listNameGame = new ArrayList<>();
    private ArrayList<Integer> listScore = new ArrayList<>();
    
    private HotArea back;
    
    public ScorePanel(FlappyBirdFrame pFlappyBirdFrame) throws SQLException{
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.flappyBirdFrame = pFlappyBirdFrame;
        this.addMouseListener(new BackMouseListener());
        
        this.back = new HotArea();
        this.back.symbol = Resources.getImage("/images/back.png");
        this.back.width = 300;
        this.back.height = 95;
        this.back.x = this.getWidth() / 2 - 350;
        this.back.y = this.getHeight() - 140;
        
        Font font = new Font("Harrington", Font.BOLD, 20);
        
        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setFont(font);
        textArea.setBackground(Color.RED);
        textArea.setForeground(Color.BLACK);
        jScrollPane = new JScrollPane(textArea);
        
        this.jScrollPane.setBounds( (this.getWidth() - 600) / 2, 170, 600, 450);
        this.add(jScrollPane);
        
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getDataFromDb();
        
        if(!listNamePlayer.isEmpty()) {
            for (int i = 0; i < listNamePlayer.size(); i++) {
                String record = "\n".concat(listNamePlayer.get(i).concat("                           ")
                        .concat(listNameGame.get(i)).concat("                           ")
                        .concat(String.valueOf(listScore.get(i))));

                textArea.append(record + "\n\n---------------------------------------------------------------------------------\n");
            }
        }
        else{
            textArea.append("\n\nNon ci sono punteggi di giochi!");
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Color color = new Color(130, 192, 255);
        g.setColor(color);

        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        color = new Color(255, 066, 030);

        g.setColor(color);
        g.fillRoundRect(20, 20, 760, 100, 80, 80);

        g.setFont(new Font("Harrington", 1, 75));
        g.setColor(Color.white);

        g.drawString("SCORE PLAYERS", 65, 100);
        
        this.back.draw(g);
    }
    
    private void getDataFromDb() throws SQLException {                
        try {
            con = ConnectionManager.getConnection();
            
            String sql = "SELECT p.nome, g.nome_gioco, pu.punteggio"
                    + " FROM player as p INNER JOIN punteggio as pu ON p.id = pu.id_player"
                    + " INNER JOIN gioco as g ON g.id = pu.id_gioco";
            
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery(); 
            
            while(rs.next()){
                listNamePlayer.add(rs.getString("nome"));
                listNameGame.add(rs.getString("nome_gioco"));
                listScore.add(rs.getInt("punteggio"));
            }
            
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed!");
        }
    }
    
    
    public class BackMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (back.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.playerSelection, flappyBirdFrame.scorePanel);
            }
        }
    }
}

