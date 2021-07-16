package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PlayerSelection extends JPanel {

    private FlappyBirdFrame flappyBirdFrame;

    public PlayerSelectionHeader header;
    public PlayerSelectionContent content;
    public PlayerSelectionFooter footer;

    public PlayerSelection(FlappyBirdFrame pFlappyBirdFrame) {

        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.flappyBirdFrame = pFlappyBirdFrame;

        this.header = new PlayerSelectionHeader(this.getSize());
        this.header.setLocation(0, 0);
        this.add(header);

        this.content = new PlayerSelectionContent(this.getSize(), flappyBirdFrame);
        this.content.setLocation(0, header.getY() + header.getHeight());
        this.add(content);

        this.footer = new PlayerSelectionFooter(this.getSize(), flappyBirdFrame);
        this.footer.setLocation(0, content.getY() + content.getHeight());
        this.add(footer);

        this.footer.setFocusable(true);
        this.footer.requestFocus(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Color color = new Color(130, 192, 255);
        g.setColor(color);

        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    // metto per poi prenderlo con il gamePanel in quanto è il gamePanel che crea gli oggetti player
    public String getPlayerNickname() {
        return this.content.getPlayerNickName();
    }

}
