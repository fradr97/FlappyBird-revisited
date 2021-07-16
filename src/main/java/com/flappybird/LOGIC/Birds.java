package com.flappybird.LOGIC;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Birds extends Rectangle{
    private Image img_birds;
    
    public int speed;
    
    //private static final int moreSpeed = 1;
    
    public Birds(Image image, int x, int y, int width, int height){
        
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.img_birds = image;

        this.speed = 10;
    }
    
    public void scrollBirds(){
        this.x = this.x -speed;
    }
 
    public void setSpeed(int speed) {

        this.speed = speed;
    }

    public int getSpeed() {

        return this.speed;
    }

    public Rectangle getBordi() {

        return new Rectangle(x, y, width, height);
    }

    public void disegna(Graphics g) {

        g.drawImage(img_birds, x, y, width, height, null);
    }
       
}
