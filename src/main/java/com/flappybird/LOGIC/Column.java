package com.flappybird.LOGIC;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Column {

    public int x;
    public int y;

    public int width;
    public int height;

    private Image img_column;

    public int speed;
    private static final int moreSpeed = 1;

    public Column(Image image, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.img_column = image;

        this.speed = 10;
    }

    //Scorrimento colonna
    public void scrollColumn() {

        this.x = this.x - speed;
    }

    public void setSpeed(int speed) {

        this.speed = speed;
    }

    public int getSpeed() {

        return this.speed;
    }

    //Aggiorno la velocità
    public void updatesSpeed() {

        this.setSpeed(this.getSpeed() + moreSpeed);
    }

    public Rectangle getBordi() {

        return new Rectangle(x, y, width, height);
    }

    public void disegna(Graphics g) {

        g.drawImage(img_column, x, y, width, height, null);
    }

}
