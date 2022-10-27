package com.griffty.Launcher;

import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

public class StatisticMenu extends JFrame {
    private JPanel mainPanel = new JPanel();
    private Font TopFont = new Font("comic sans ms", Font.BOLD, 30);
    private final StatisticPanel[] allGames = {null, null, null, null, null, null};
    public static final String[] allGamesNames = {"Baker's Dozen", "Word Builder", "Maze", "Wizard Yes Or No", "Sliding Tiles", "Watch Your Step"};
    StatisticMenu(){
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
        TitleLabel titleLabel = new TitleLabel("Statistic", this);
        mainPanel.add(titleLabel, PAGE_START);
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(0,3));
        centralPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(centralPanel, CENTER);

        for (int i = 0; i <6; i++){
            allGames[i] = new StatisticPanel(allGamesNames[i]);
            centralPanel.add(allGames[i]);
        }
    }
}
