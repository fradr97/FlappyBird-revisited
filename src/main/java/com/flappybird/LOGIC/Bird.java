package com.flappybird.LOGIC;

import com.flappybird.GUI.PANELS.GamePanelShootRunBirds;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Bird extends Rectangle{
    private static final int jump = 50; //ampiezza salto
    public static final int down = 2; //discesa
    
    private static final int right = 2;
    private static final int left = 2;
    
    private static final int upRun = 6;
    private static final int downRun = 6;
    
    private boolean active = false;

    private Image img_bird;

    GamePanelShootRunBirds gameShootRun;

    public Bird(Image image, int x, int y, int width, int height) {

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.img_bird = image;
    }
    
    public void birdJump() {
        if (active == true) {
            this.y -= jump;
        }
    }

    public void birdDown() {
        this.y += down;
    }

    public void birdLeft(int pY, int velocity) {
        this.x -= left * velocity;
        this.y += pY;
    }
    
    public void birdUpRun() {
        this.y -= upRun;
    }
    
    public void birdDownRun(){
        this.y += downRun;
    }
    
    public void birdRight(int velocity) {
        this.x += right * velocity;
    }
    
    public void birdDown(int velocity) {
        this.y += down * velocity;
    }
    
    public void birdUp(int velocity) {
        this.y += jump * velocity;
    }
    
    //Attivazione salto
    public void setActiveJump(boolean pActive) {
        this.active = pActive;
    }
    
    public void spara(){
    }

    public Rectangle getBordi() {
        return new Rectangle(x, y, width, height);
    }

    public void disegna(Graphics g) {
        g.drawImage(img_bird, x, y, width, height, null);
    }
    
    public boolean isCentered(MouseEvent e){
        return this.contains(e.getPoint());
    }
    
    public void deadBird(){
        this.x = -1000000;
        this.y = -1000000;
    }
}
