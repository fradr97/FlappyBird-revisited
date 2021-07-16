package com.flappybird.LOGIC;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class HotArea extends Rectangle {

    public Image symbol;

    private boolean attivo = true;

    public boolean isClicked(MouseEvent e) {
        if (attivo) {
            return this.contains(e.getPoint());
        }

        return false;
    }

    public void draw(Graphics g) {
        if (attivo) {
            g.drawImage(symbol, x, y, width, height, null);
        }
    }

    //Disattivo e attivo un hotArea
    public void isActive(boolean pAttiva) {
        this.attivo = pAttiva;
    }

}
