package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

public class Launcher extends JFrame {
    private Font startBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);
    private Font settingsBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 64);
    private Font MainLabelFont = new Font("comic sans ms", Font.BOLD, 192);
    public static Dimension windowSize;

    private final JPanel centralPanel = new JPanel();
    private final JLabel gameName = new JLabel("Game Hub");
    private final JButton startButton = new JButton("Start playing");
    private final JButton settingsButton = new JButton();
    Launcher(){
        initGUI();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private void initGUI(){
        add(centralPanel);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setPreferredSize(new Dimension(938,510));
        centralPanel.add(Box.createVerticalGlue());

        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameName.setFont(MainLabelFont);
        centralPanel.add(gameName);
        centralPanel.add(Box.createVerticalGlue());
        startButton.setFont(startBtnFont);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            windowSize = new Dimension(centralPanel.getWidth(), centralPanel.getHeight());
            removeAll();
            newProfileChooser();
            dispose();
        });
        centralPanel.add(startButton, CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        settingsButton.setText("Settings");
        settingsButton.setFont(settingsBtnFont);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.add(settingsButton);
        centralPanel.add(Box.createVerticalGlue());


        windowSize = new Dimension(centralPanel.getWidth(), centralPanel.getHeight());
        startBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 510/18);
        startButton.setFont(startBtnFont);
        MainLabelFont = new Font("comic sans ms", Font.BOLD, 510/6);
        gameName.setFont(MainLabelFont);
        settingsBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 510/24);
        settingsButton.setFont(settingsBtnFont);
    }
    public static void newProfileChooser(){
        new profileChooser(windowSize);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                String className = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(className);
                new Launcher();
            } catch (Exception ignored) {}
        });
    }


}
