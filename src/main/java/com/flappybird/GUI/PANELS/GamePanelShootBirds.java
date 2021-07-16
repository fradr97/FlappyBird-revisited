package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.Bird;
import com.flappybird.LOGIC.Game;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.LOGIC.Player;
import com.flappybird.LOGIC.exceptions.InvalidInputException;
import com.flappybird.UTILS.Resources;
import com.flappybird.UTILS.Sound;
import com.flappybird.connectionDb.ConnectionManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class GamePanelShootBirds extends JPanel{
    private Connection con = null;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    
    private FlappyBirdFrame flappyBirdFrame;
    public StatusBarPanelShootingBirds statusBarPanelShootingBirds;
    
    public Game game;
    private Player player;
    
    private static final int numberRedBirds = 120;
    private static final int numberYellowBirds = 100;
    private static final int numberEvilBirds = 10;
    private static final int numberBonusBird = 3;
    private static final int numberMiniEvilBird = 100;
    
    private Bird bird[] = new Bird[numberRedBirds];
    private Bird birdLeft[] = new Bird[numberYellowBirds];
    private Bird birdTopEvil[] = new Bird[numberEvilBirds];
    private Bird birdBonus[] = new Bird[numberBonusBird];
    private Bird birdMiniEvil[] = new Bird[numberMiniEvilBird];
    
    private Image image_redBird;
    private Image image_yellowBird;
    private Image image_evilBird;
    private Image image_bonusBird;
    private Image image_miniEvilBird;
    private Image background;
    
    private Image imageCursor;
    
    private Sound collision;
    private Sound sparo;

    public HotArea start;
    
    private boolean started = false;
    private boolean running;
    
    
    public GamePanelShootBirds(FlappyBirdFrame pFlappyBirdFrame){
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.flappyBirdFrame = pFlappyBirdFrame;
        this.addMouseListener(new MouseStart());
        this.addMouseListener(new MouseShootBirds());
        
        this.statusBarPanelShootingBirds = new StatusBarPanelShootingBirds(pFlappyBirdFrame);
        this.statusBarPanelShootingBirds.setBounds(0, 0, FlappyBirdFrame.WIDTH_FRAME, 40);
        this.add(statusBarPanelShootingBirds);
        
        this.background = Resources.getImage("/images/sfondoN.png");
        this.image_redBird = Resources.getImage("/images/BIRD.png");
        this.image_yellowBird = Resources.getImage("/images/yellowBird.png");
        this.image_evilBird = Resources.getImage("/images/blackBird.png");
        this.image_bonusBird = Resources.getImage("/images/bonusBird.png");
        this.image_miniEvilBird = Resources.getImage("/images/redEvilBird.png");
        
        this.sparo = Resources.getSound("/sound/sparo.wav");
        this.collision = Resources.getSound("/sound/boom.wav");
        
        this.imageCursor = Resources.getImage("/images/cursor.png");
        
        Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(imageCursor, new Point(0,0),"mio_cursore");
        this.setCursor(myCursor);
        
        this.start = new HotArea();
        this.start.symbol = Resources.getImage("/images/click.png");
        this.start.width = 80;
        this.start.height = 80;
        this.start.x = this.getWidth() / 2 - 45;
        this.start.y = this.getHeight() / 3 - 100;
        
        this.addComponent();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());     
        
        g.drawImage(background, 0, 0, FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME, null);

        
        this.start.draw(g);
        
        for (int i = 0; i < numberRedBirds; i ++) {
            this.bird[i].disegna(g);
        }
        
        for (int i = 0; i < numberYellowBirds; i ++) {
            this.birdLeft[i].disegna(g);
        }
        
        for (int i = 0; i < numberEvilBirds; i ++) {
            this.birdTopEvil[i].disegna(g);
        }
        
        for (int i = 0; i < numberBonusBird; i ++) {
            this.birdBonus[i].disegna(g);
        }
        
        for (int i = 0; i < numberMiniEvilBird; i ++) {
            this.birdMiniEvil[i].disegna(g);
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("Harrington", 1, 100));

        if (started == false) {
            g.drawString("Click to Play!", 75, this.getHeight() / 2 - 50);
        } else {
            g.drawString(game.player.getScore() + "", this.getWidth() / 2 - 25, 120);
        }
    }
    
    public void startGame(Player pPlayer) {
        try {
            this.player = pPlayer;
            this.game = new Game(this.player);
            this.game.start();
            
        } catch (InvalidInputException ex) {
            Logger.getLogger(GamePanelFlappyClassic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startTimer() {
        this.statusBarPanelShootingBirds.startTimer();
    }

    public void stopTimer() {
        this.statusBarPanelShootingBirds.stopTimer();
    }
    
    public void startFlightBird() {
        this.running = true;

        Thread flight = new Thread(new ThreadFlightBird());
        flight.start();
    }
    
    public void startFlightBirdLeft() {
        this.running = true;

        Thread flight = new Thread(new ThreadFlightBirdLeft());
        flight.start();
    }
    
    public void startFlightBirdEvil() {
        this.running = true;

        Thread flight = new Thread(new ThreadFlightBirdEvil());
        flight.start();
    }
    
    public void startFlightBirdBonus() {
        this.running = true;

        Thread flight = new Thread(new ThreadFlightBirdBonus());
        flight.start();
    }
    
    public void startFlightBirdMiniEvil() {
        this.running = true;

        Thread flight = new Thread(new ThreadFlightBirdMiniEvil());
        flight.start();
    }

    
    public void stopFlight() {
        this.running = false;
    }
    
    private void addComponent() {    
        
        Random r = new Random();
        int spazioX = 20;        
        for (int i = 0; i < numberRedBirds; i++) {
            int y = r.nextInt(750);
            this.bird[i] = new Bird(image_redBird, -(spazioX * i),  y, 45, 45);
        }
        
        Random r1 = new Random();
        int spazioy = 20;
        int spaziox = 100; 
        for (int i = 0; i < numberYellowBirds; i++) {
            int y1 = r1.nextInt(700);
            this.birdLeft[i] = new Bird(image_yellowBird, 500 + y1 +(spaziox * i), -y1, 45, 45);
        }
        
        Random r2 = new Random();
        int spazio2 = 200;
        for (int i = 0; i < numberEvilBirds; i++) {
            int y2 = r2.nextInt(750);
            this.birdTopEvil[i] = new Bird(image_evilBird, y2, -500 - y2 + (i * spazio2), 45, 45);
        }
        
        Random r3 = new Random();
        for (int i = 0; i < numberBonusBird; i++) {
            int x = r3.nextInt(750);  
            this.birdBonus[i] = new Bird(image_bonusBird, x, 3000 + (x + spazio2 * i), 45, 45);
        }
        
        Random r4 = new Random();
        for (int i = 0; i < numberMiniEvilBird; i++) {
            int y4 = r4.nextInt(750);   //max 750 senno esce fuori
            this.birdMiniEvil[i] = new Bird(image_miniEvilBird, y4 + 100, y4 + (i * y4), 45, 45);
        }
    }
    
    public void gameOver(boolean gameOver) throws SQLException {
        if (this.statusBarPanelShootingBirds.getSeconds() == 60 || gameOver) {
            game.setStatus(Game.END_GAME);
            
            this.collision.play();
            
            stopTimer();
            stopFlight();
            
            if (player.getScore() > player.getBestScore()) {
                game.updatesBestScore();
            }
            
            saveScore();
            flappyBirdFrame.switchPanel(flappyBirdFrame.endPanelShootBirds, flappyBirdFrame.gamePanelShootBirds);
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
            }
            else
                result = 0;
            
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed!");
        }
        
        return result;
    }
    
    private void saveScore() throws SQLException {
        try {
            int id = getIdPlayerFromDb(player.getNickname());
            
            
            con = ConnectionManager.getConnection();

            if (id > 0) {
                con = ConnectionManager.getConnection();

                // the mysql insert statement
                String sql = "INSERT INTO punteggio(id_player, id_gioco, punteggio)"
                        + " values (?, ?, ?)";

                // create the mysql insert preparedstatement
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, 2);
                preparedStatement.setInt(3, player.getScore());

                // execute the preparedstatement
                preparedStatement.execute();

                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed insert!");
        }
    }
    
    public void resetGame() {
        game.setStatus(Game.READY_TO_START);

        player.setScore(0);
        statusBarPanelShootingBirds.setSeconds(0);

        addComponent();

        start.isActive(true); //riattivo il tasto per partire
    }
    
    private class MouseStart extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            if (start.isClicked(e)) {
                game.setStatus(Game.ONGOING);
                startFlightBird();
                startFlightBirdLeft();
                startFlightBirdEvil();
                startFlightBirdBonus();
                startFlightBirdMiniEvil();
                startTimer();
                started = true;
                
                start.isActive(false);
                statusBarPanelShootingBirds.pause.isActive(true);
                repaint();
            }
        }
    }
    
    private class ThreadFlightBird implements Runnable{
        @Override
        public void run(){
            try{
                while (running) {
                    for (int i = 0; i < numberRedBirds; i++) {
                        bird[i].birdRight(5);

                        Thread.sleep(1);
                        repaint();
                    }
                    gameOver(false);
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class ThreadFlightBirdLeft implements Runnable{
        @Override
        public void run(){
            try{
                while (running) {
                    for (int i = 0; i < numberYellowBirds; i++) {
                        birdLeft[i].birdLeft(2, 5);

                        Thread.sleep(1);
                        repaint();
                    }
                    gameOver(false);
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class ThreadFlightBirdEvil implements Runnable{
        @Override
        public void run(){
            try{
                while (running) {
                    for (int i = 0; i < numberEvilBirds; i++) {
                        birdTopEvil[i].birdDown(1);

                        Thread.sleep(5);
                        repaint();
                    }
                    gameOver(false);
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class ThreadFlightBirdBonus implements Runnable{
        @Override
        public void run(){
            try{
                while (running) {
                    for (int i = 0; i < numberBonusBird; i++) {
                        birdBonus[i].birdUp(-1);

                        Thread.sleep(100);
                        repaint();
                    }
                    gameOver(false);
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ThreadFlightBirdMiniEvil implements Runnable{
        @Override
        public void run(){
            try{
                while (running) {
                    for (int i = 0; i < numberMiniEvilBird; i++) {
                        birdMiniEvil[i].birdUp(-1);

                        Thread.sleep(15);
                        repaint();
                    }
                    gameOver(false);
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class MouseShootBirds extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (started) {
                for (int i = 0; i < numberRedBirds; i++) {
                    if (bird[i].isCentered(e)) {
                        bird[i].deadBird();
                        game.increaseScore(1);
                    }
                }
                for (int i = 0; i < numberYellowBirds; i++) {
                    if (birdLeft[i].isCentered(e)) {
                        birdLeft[i].deadBird();
                        game.increaseScore(2);
                    }
                }
                for (int i = 0; i < numberEvilBirds; i++) {
                    if (birdTopEvil[i].isCentered(e)) {
                        try {
                            gameOver(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(GamePanelShootBirds.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                for (int i = 0; i < numberBonusBird; i++) {
                    if (birdBonus[i].isCentered(e)) {
                        birdBonus[i].deadBird();
                        game.increaseScore(10);
                    }
                }
                for (int i = 0; i < numberMiniEvilBird; i++) {
                    if (birdMiniEvil[i].isCentered(e)) {
                        birdMiniEvil[i].deadBird();
                        game.increaseScore(-1);
                    }
                }
                sparo.play();
            }
        }
    }
    
    
}
