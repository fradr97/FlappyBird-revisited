package com.flappybird.GUI;

import com.flappybird.GUI.PANELS.ChoiceGamesPanel;
import com.flappybird.GUI.PANELS.EndPanel;
import com.flappybird.GUI.PANELS.EndPanelShootBirds;
import com.flappybird.GUI.PANELS.EndPanelShootRunBird;
import com.flappybird.GUI.PANELS.WelcomeScreen;
import com.flappybird.GUI.PANELS.StopPanel;
import com.flappybird.GUI.PANELS.PlayerSelection;
import com.flappybird.GUI.PANELS.GamePanelFlappyClassic;
import com.flappybird.GUI.PANELS.GamePanelShootBirds;
import com.flappybird.GUI.PANELS.GamePanelShootRunBirds;
import com.flappybird.GUI.PANELS.RegistrationPanel;
import com.flappybird.GUI.PANELS.ScorePanel;
import com.flappybird.GUI.PANELS.StopPanelShootBirds;
import com.flappybird.GUI.PANELS.StopPanelShootRunBird;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FlappyBirdFrame extends JFrame {

    public static final int WIDTH_FRAME = 800;
    public static final int HEIGHT_FRAME = 800;

    public WelcomeScreen welcomeScreen;
    public PlayerSelection playerSelection;
    public ScorePanel scorePanel;
    public ChoiceGamesPanel choiceGamesPanel;
    public GamePanelShootBirds gamePanelShootBirds;
    public GamePanelShootRunBirds gamePanelShootRunBirds;
    public RegistrationPanel registrationPanel;
    public GamePanelFlappyClassic gamePanelFlappyClassic;
    public EndPanel endPanel;
    public EndPanelShootBirds endPanelShootBirds;
    public EndPanelShootRunBird endPanelShootRunBird;
    public StopPanel stopPanel;
    public StopPanelShootBirds stopPanelShootBirds;
    public StopPanelShootRunBird stopPanelShootRunBird; 

    public FlappyBirdFrame() throws SQLException {

        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("FlappyBird");

        this.welcomeScreen = new WelcomeScreen(this);
        this.welcomeScreen.setLocation(0, 0);
        this.getContentPane().add(welcomeScreen);

        this.scorePanel = new ScorePanel(this);
        this.scorePanel.setLocation(0, 0);
        this.getContentPane().add(scorePanel);
        
        this.playerSelection = new PlayerSelection(this);
        this.playerSelection.setLocation(0, 0);
        this.getContentPane().add(playerSelection);

        this.choiceGamesPanel = new ChoiceGamesPanel(this);
        this.choiceGamesPanel.setLocation(0, 0);
        this.getContentPane().add(choiceGamesPanel);

        this.registrationPanel = new RegistrationPanel(this);
        this.registrationPanel.setLocation(0, 0);
        this.getContentPane().add(registrationPanel);

        this.gamePanelFlappyClassic = new GamePanelFlappyClassic(this);
        this.gamePanelFlappyClassic.setLocation(0, 0);
        this.getContentPane().add(gamePanelFlappyClassic);
        
        this.gamePanelShootBirds = new GamePanelShootBirds(this);
        this.gamePanelShootBirds.setLocation(0, 0);
        this.getContentPane().add(gamePanelShootBirds);

        this.gamePanelShootRunBirds = new GamePanelShootRunBirds(this);
        this.gamePanelShootRunBirds.setLocation(0, 0);
        this.getContentPane().add(gamePanelShootRunBirds);        
        
        this.stopPanel = new StopPanel(this);
        this.stopPanel.setLocation(0, 0);
        this.getContentPane().add(stopPanel);
        
        this.stopPanelShootBirds = new StopPanelShootBirds(this);
        this.stopPanelShootBirds.setLocation(0, 0);
        this.getContentPane().add(stopPanelShootBirds);

        this.stopPanelShootRunBird = new StopPanelShootRunBird(this);
        this.stopPanelShootRunBird.setLocation(0, 0);
        this.getContentPane().add(stopPanelShootRunBird);
        
        this.endPanel = new EndPanel(this);
        this.endPanel.setLocation(0, 0);
        this.getContentPane().add(endPanel);
        
        this.endPanelShootBirds = new EndPanelShootBirds(this);
        this.endPanelShootBirds.setLocation(0, 0);
        this.getContentPane().add(endPanelShootBirds);

        this.endPanelShootRunBird = new EndPanelShootRunBird(this);
        this.endPanelShootRunBird.setLocation(0,0);
        this.getContentPane().add(endPanelShootRunBird);

        this.welcomeScreen.setVisible(true);
        this.playerSelection.setVisible(false);
        this.scorePanel.setVisible(false);
        this.choiceGamesPanel.setVisible(false);
        this.gamePanelFlappyClassic.setVisible(false);
        this.gamePanelShootBirds.setVisible(false);
        this.gamePanelShootRunBirds.setVisible(false);
        this.stopPanel.setVisible(false);
        this.stopPanelShootBirds.setVisible(false);
        this.stopPanelShootRunBird.setVisible(false);
        this.endPanel.setVisible(false);
        this.endPanelShootBirds.setVisible(false);
        this.endPanelShootRunBird.setVisible(false);
        this.registrationPanel.setVisible(false);
    }

    // PERMETTE LO SWITCH DEI PANNELLI DANDO IL FOCUS A QUELLO VISIBILE
    public void switchPanel(JPanel panelToBeEnabled, JPanel panelToBeDisabled) {
        panelToBeDisabled.setVisible(false);
        panelToBeEnabled.setVisible(true);

        panelToBeDisabled.setFocusable(false);
        panelToBeEnabled.setFocusable(true);

        panelToBeEnabled.requestFocus(true);
    }

}
