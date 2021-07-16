package com.flappybird.GUI.PANELS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PlayerSelectionHeader extends JPanel {

    public PlayerSelectionHeader(Dimension pDimension) {

        this.setSize(pDimension.width, pDimension.height / 5);

    }

    @Override
    public void paintComponent(Graphics g) {
        Color color = new Color(255, 066, 030);

        g.setColor(color);
        g.fillRoundRect(20, 20, 760, 100, 80, 80);

        g.setFont(new Font("Harrington", 1, 75));
        g.setColor(Color.white);

        g.drawString("SELECT PLAYER ", 55, 100);

    }

}
