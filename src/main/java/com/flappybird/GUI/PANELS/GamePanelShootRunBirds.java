package com.flappybird.GUI.PANELS;

import com.flappybird.GUI.FlappyBirdFrame;
import com.flappybird.LOGIC.Bird;
import com.flappybird.LOGIC.Birds;
import com.flappybird.LOGIC.Column;
import com.flappybird.LOGIC.Game;
import com.flappybird.LOGIC.HotArea;
import com.flappybird.LOGIC.Player;
import com.flappybird.LOGIC.exceptions.InvalidInputException;
import com.flappybird.UTILS.Resources;
import com.flappybird.UTILS.Sound;
import com.flappybird.connectionDb.ConnectionManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author Emanuela
 */
public class GamePanelShootRunBirds extends JPanel{
    private Connection con = null;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    
    private FlappyBirdFrame flappyBirdFame;
    private Player player;

    public Game game;
    public StatusBarPanelShootRun statusBarPanelShootRun;

    public Bird bird;
    
    private static final int dimensione = 10000;
    private static Column column[] = new Column[dimensione];
    
    private static final int dimension = 10000;
    private static Birds birds[] = new Birds[dimension];

    private int altezza;
    private int positionBirdsx; // posizione uccelli da schivare
    private int positionBirdsy; // posizione uccelli da schivare

    private static final int space = 300; //spazio tra la colonna superiore e la colonna inferiore
    private static final int x = 350; //spazio tra le colonne
    
    private static final int spaceBird = 270; //spazio tra la colonna superiore e la colonna inferiore
    private static final int xBird = 250; //spazio tra le colonne

    private boolean started = false;
    private boolean gameOver = false;
    private boolean running;

    public HotArea start;

    private Random rand;
    private Random randomBirdy;

    private Image background;
    private Image image_bird;
    private Image image_birds;
    private Image image_birds2;
    private Image image_columnS;
    private Image image_columnI;
    private Image terra;

    private Sound sound;
    private Sound collision;
    
    public GamePanelShootRunBirds(FlappyBirdFrame pFlappyBirdFrame){
        this.setSize(FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.addMouseListener(new GamePanelShootRunBirds.MyMouseStart());

        this.flappyBirdFame = pFlappyBirdFrame;

        this.statusBarPanelShootRun = new StatusBarPanelShootRun(pFlappyBirdFrame);
        this.statusBarPanelShootRun.setBounds(0, 0, FlappyBirdFrame.WIDTH_FRAME, 40); //setBounds è l'aggergazione di setSize e setLocation
        this.add(statusBarPanelShootRun);

        this.background = Resources.getImage("/images/sfondo.jpg");
        this.terra = Resources.getImage("/images/STRISCIA.jpg");

        this.image_bird = Resources.getImage("/images/BIRD.png");
        this.image_birds = Resources.getImage("/images/whiteBirdRun.png");
        this.image_birds2 = Resources.getImage("/images/brownBirdRun.png");
        this.image_columnS = Resources.getImage("/images/colonnaS.png");
        this.image_columnI = Resources.getImage("/images/colonnaI.png");

        this.sound = Resources.getSound("/sound/laser.wav");
        this.collision = Resources.getSound("/sound/boom.wav");

        this.start = new HotArea();

        this.start.symbol = Resources.getImage("/images/click.png");
        this.start.width = 80;
        this.start.height = 80;
        this.start.x = this.getWidth() / 2 - 45;
        this.start.y = this.getHeight() / 3 - 100;

        this.rand = new Random();
        this.randomBirdy = new Random();
        
        this.addColumnBird();
        this.addBirds();

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bird.birdUpRun();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bird.birdDownRun();
                }
            }
        });

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

    public void startColumn() {
        this.running = true;

        Thread scroll = new Thread(new ThreadColumn());
        scroll.start();
    }

    public void stopColumn() {
        this.running = false;
    }

    public void startTimer() {
        this.statusBarPanelShootRun.startTimer();
    }

    public void stopTimer() {
        this.statusBarPanelShootRun.stopTimer();
    }
    public void startSpeed() {
        this.running = true;

        Thread speed = new Thread(new ThreadSpeed());
        speed.start();
    }

    public void stopSpeed() {
        this.running = false;
    }
       public void startBirds(){
        this.running = true;
        
        Thread birdsScroll = new Thread(new ThreadBirds());
        birdsScroll.start();
    }
    
    public void stopBirds() {
        this.running = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.drawImage(background, 0, 0, FlappyBirdFrame.WIDTH_FRAME, FlappyBirdFrame.HEIGHT_FRAME, null);
        g.drawImage(terra, 0, 680, FlappyBirdFrame.WIDTH_FRAME, 140, null);

        this.start.draw(g);

        this.bird.disegna(g);

        for (int i = 0; i < dimensione; i++) {
            this.column[i].disegna(g);
        }
        
        for (int i = 0; i < dimension; i++) {
            this.birds[i].disegna(g);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Harrington", 1, 100));

        if (started == false) {
            g.drawString("Click to Play!", 75, this.getHeight() / 2 - 50);
        } else {
            g.drawString(game.player.getScore() + "", this.getWidth() / 2 - 25, 120);
        }

    }

    //AGGIUNGO L'UCCELLO E LE COLONNE REGOLANDO QUELLE INFERIORI E SUPERIORI, CON ALTEZZA RANDOM
    public void addColumnBird() {

        this.bird = new Bird(image_bird, FlappyBirdFrame.WIDTH_FRAME / 2 - 10, FlappyBirdFrame.HEIGHT_FRAME / 2 - 10, 45, 45);

        altezza = 50 + rand.nextInt(300);
        //creo le colonne  
        for (int i = 0; i < dimensione; i++) {
            if (i % 2 == 0) {
                this.column[i] = new Column(image_columnI, FlappyBirdFrame.WIDTH_FRAME + 100 + x * (i), FlappyBirdFrame.HEIGHT_FRAME - altezza - 120, 100, altezza);
            } else {
                this.column[i] = new Column(image_columnS, FlappyBirdFrame.WIDTH_FRAME + 100 + x * (i), 0, 100, FlappyBirdFrame.HEIGHT_FRAME - altezza - space);
                altezza = 50 + rand.nextInt(300);
            }
        }
    }
    
    public void addBirds(){
        
        positionBirdsy = 50 + randomBirdy.nextInt(300);

        for (int i = 0; i < dimension; i++) {
            if (i % 2 == 0) {
                this.birds[i] = new Birds(image_birds, FlappyBirdFrame.WIDTH_FRAME + 100 + xBird *(i) , FlappyBirdFrame.HEIGHT_FRAME - positionBirdsy - spaceBird, 40, 40);
            } else {
                this.birds[i] = new Birds(image_birds2, FlappyBirdFrame.WIDTH_FRAME + 200 + xBird * (i), FlappyBirdFrame.HEIGHT_FRAME - positionBirdsy - spaceBird, 40, 40);
                positionBirdsy = 50 + randomBirdy.nextInt(300);
                
            }

        }
    }

    public void scoreCounts() {

        for (int i = 0; i < dimensione; i++) {

            if ((bird.x + bird.width / 2 > column[i].x + column[i].width / 2 - 15) && (bird.x + bird.width / 2 < column[i].x + column[i].width / 2)) {
                game.increaseScore();
            }
        }

    }

    public boolean collision() {

        for (int i = 0; i < dimensione; i++) {
            if (bird.getBordi().intersects(column[i].getBordi())) {
                return true;
            }
        }
        
        for (int i = 0; i < dimension; i++) {
            if (bird.getBordi().intersects(birds[i].getBordi())) {
                return true;
            }
        }
        //COLLISIONI SUPERIORI E INFERIORI PANNELLO
        if ((bird.y <= 43) || (bird.y + bird.height >= FlappyBirdFrame.HEIGHT_FRAME - 120)) {
            return true;
        }
        return false;
    }

    public void checkGameOver() throws SQLException {
        if (this.collision() == true) {
            this.statusBarPanelShootRun.pause.isActive(false); //disattivo il pulsante pause
            flappyBirdFame.switchPanel(flappyBirdFame.endPanelShootRunBird, flappyBirdFame.gamePanelShootRunBirds);

            this.collision.play();

            game.setStatus(Game.END_GAME);
            stopColumn();
            stopTimer();
            stopBirds();
            stopSpeed();

            if (player.getScore() > player.getBestScore()) {
                game.updatesBestScore();
            }
            
            saveScore();
        }
    }

    public void resetGame() {

        game.setStatus(Game.READY_TO_START);

        player.setScore(0);
        statusBarPanelShootRun.setSeconds(0);

        addColumnBird();
        addBirds();

        start.isActive(true); //riattivo il tasto per partitre

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
                System.out.println("result: " + result);
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
                preparedStatement.setInt(2, 3);
                preparedStatement.setInt(3, player.getScore());

                // execute the preparedstatement
                preparedStatement.execute();

                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed insert!");
        }
    }

    private class MyMouseStart extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (start.isClicked(e)) {

                game.setStatus(Game.ONGOING);
                startColumn();
                startTimer();
                startBirds();
                startSpeed();

                started = true;

                bird.setActiveJump(true);  //imposto a true il click dello space solo quando parto

                start.isActive(false);
                sound.play();
                statusBarPanelShootRun.pause.isActive(true);

            }
        }

    }


    private class ThreadDown implements Runnable {

        @Override
        public void run() {
            while (running) {
                if (started == true) {
                    repaint();
                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException ex) {

                    Logger.getLogger(GamePanelFlappyClassic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    private class ThreadColumn implements Runnable {

        @Override
        public void run() {
            while (running) {
                if (started == true) {
                    for (int i = 0; i < dimensione; i++) {
                        column[i].scrollColumn();
                    }

                    scoreCounts();
                    try {
                        checkGameOver();
                    } catch (SQLException ex) {
                        Logger.getLogger(GamePanelShootRunBirds.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                }
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanelFlappyClassic.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }
    
        private class ThreadBirds implements Runnable {

        @Override
        public void run() {
            while (running) {
                if (started == true) {
                    for (int i = 0; i < dimension; i++) {
                        birds[i].scrollBirds();
                    }
                    try {
                        checkGameOver();
                    } catch (SQLException ex) {
                        Logger.getLogger(GamePanelShootRunBirds.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                }
                try {
                    Thread.sleep(160);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanelShootRunBirds.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }
        
     private class ThreadSpeed implements Runnable {

        @Override
        public void run() {
            while (running) {
                if (started == true) {
                    for (int i = 0; i < dimensione; i++) {
                        column[i].updatesSpeed();
                    }

                }

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanelShootRunBirds.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
