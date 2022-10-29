package com.griffty.Launcher;
import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.griffty.Launcher.Launcher.DarkTheme;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

public class GameMenu extends JFrame {
    private final JPanel mainPanel = new JPanel();
    private final GamePanel[] allGames = {null, null, null, null, null, null};
    public static final String[] allGamesNames = {"Baker's Dozen", "Word Builder", "Maze", "Wizard Yes Or No", "Sliding Tiles", "Watch Your Step"};
    private final Font statisticFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    private final User user = profileChooser.user;
    GameMenu(){
        try {
            String className = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        }catch (Exception ignored){}
        initGui();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void initGui(){
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        TitleLabel titleLabel = new TitleLabel("Choose what do you want to play:",this);
        mainPanel.add(titleLabel, PAGE_START);
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(2,3, 10, 10));
        centralPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(centralPanel, CENTER);

        for (int i = 0; i <6; i++){
            allGames[i] = new GamePanel(allGamesNames[i]);
            allGames[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    GamePanel gamePanel = (GamePanel) e.getSource();
                    gamePanel.startGame();
                    dispose();
                }
            });
            centralPanel.add(allGames[i]);
        }
        JPanel bottomPanel = new JPanel();
        if (user!=null) {
            mainPanel.add(bottomPanel);
            JColoredButton statButton = new JColoredButton("Open Statistic");
            statButton.setFont(statisticFont);
            statButton.addActionListener(e -> {
                new StatisticMenu();
                dispose();
            });
            bottomPanel.add(statButton);
        }
        if (DarkTheme){
            centralPanel.setBackground(Color.darkGray);
            bottomPanel.setBackground(Color.darkGray);
        }
    }
}