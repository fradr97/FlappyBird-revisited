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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrationPanel extends JPanel {

    private Connection con = null;
    private Statement stmt = null;

    private FlappyBirdFrame flappyBirdFrame;

    private JTextField fieldName;

    private HotArea confirmRegistration;
    private HotArea back;

    public RegistrationPanel(FlappyBirdFrame pFlappyBirdFrame) {
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.flappyBirdFrame = pFlappyBirdFrame;
        this.addMouseListener(new IniziaPartitaMouse());

        this.fieldName = new JTextField("Your nickname");
        this.fieldName.setFont(new Font("Harrington", 1, 30));
        this.fieldName.setBounds(this.getWidth() / 4 - 50, this.getHeight() / 4, (int) (this.getWidth() / 1.5), this.getHeight() / 8);
        this.add(fieldName);

        this.confirmRegistration = new HotArea();
        this.back = new HotArea();

        this.confirmRegistration.symbol = Resources.getImage("/images/PLAY.png");
        this.confirmRegistration.width = 300;
        this.confirmRegistration.height = 100;
        this.confirmRegistration.x = this.getWidth() / 2 + 40;
        this.confirmRegistration.y = this.getHeight() - 140;

        this.back.symbol = Resources.getImage("/images/back.png");
        this.back.width = this.confirmRegistration.width;
        this.back.height = this.confirmRegistration.height - 5;
        this.back.x = this.getWidth() / 2 - 350;
        this.back.y = this.confirmRegistration.y;

    }

    public String getPlayerNickName() {
        return fieldName.getText();
    }

    private void registration(String nickname) throws SQLException {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();

            String sql = "INSERT INTO player(nome) values('" + nickname + "')";

            stmt.executeUpdate(sql);

            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to create the database connection.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color color = new Color(130, 192, 255);
        g.setColor(color);
        g.fillRect(0, 0, FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        
        Color colorWrite = new Color(255, 066, 030);

        g.setColor(colorWrite);
        g.fillRoundRect(20, 20, 760, 100, 80, 80);

        g.setFont(new Font("Harrington", 1, 75));
        g.setColor(Color.white);

        g.drawString("SIGN-IN PLAYER", 95, 100);

        this.confirmRegistration.draw(g);
        this.back.draw(g);
    }

    public class IniziaPartitaMouse extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (confirmRegistration.isClicked(e)) {
                try {
                    registration(getPlayerNickName());
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                flappyBirdFrame.switchPanel(flappyBirdFrame.playerSelection, flappyBirdFrame.registrationPanel);
            }
            if (back.isClicked(e)) {
                flappyBirdFrame.switchPanel(flappyBirdFrame.playerSelection, flappyBirdFrame.registrationPanel);
            }
        }
    }
}
