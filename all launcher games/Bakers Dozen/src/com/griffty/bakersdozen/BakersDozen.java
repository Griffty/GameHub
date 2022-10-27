package com.griffty.bakersdozen;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

import static java.awt.BorderLayout.*;
import static java.awt.Color.black;

public class BakersDozen extends JFrame {
    private User user;
    @Serial
    private static final long serialVersionUID = 1L;
    TablePanel tablePanel;


    public BakersDozen(){
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        user.bakersDozenStatistic.addAttempt();
        initGUI();
        setTitle("Baker's Dozen");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void initGUI(){
        TitleLabel titleLabel = new TitleLabel("Baker's Dozen", this);
        add(titleLabel, PAGE_START);
        // table panel
        tablePanel = new TablePanel(user);
        add(tablePanel, CENTER);
        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(black);
        add(buttonPanel, PAGE_END);
        JButton newGameButton = new JButton("New game");
        newGameButton.addActionListener(e -> {
            user.bakersDozenStatistic.addAttempt();
            tablePanel.newGame();
        });
        buttonPanel.add(newGameButton);
        JButton replayGameButton = new JButton("Replay");
        replayGameButton.addActionListener(e -> {
            user.bakersDozenStatistic.addAttempt();
            tablePanel.replay();

        });
        buttonPanel.add(replayGameButton);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                String className = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(className);
                new BakersDozen();
            } catch (Exception ignored) {}
        });
    }
}
