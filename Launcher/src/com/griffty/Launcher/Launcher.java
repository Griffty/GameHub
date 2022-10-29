package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

public class Launcher extends JFrame {
    public static Dimension windowSize = new Dimension(938,510);
    private final JPanel centralPanel = new JPanel();
    private final JLabel gameName = new JLabel("Game Hub");
    private final JColoredButton startButton = new JColoredButton("Start playing");
    private final JColoredButton darkTheme = new JColoredButton("Dark theme");
    public static boolean DarkTheme = false;
    private Color textColor = new Color(200, 200, 200);

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

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setPreferredSize(new Dimension(600,510));
        centralPanel.add(Box.createVerticalGlue());
        add(centralPanel);

        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.add(gameName);
        centralPanel.add(Box.createVerticalGlue());
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            new profileChooser(windowSize);
            dispose();
        });
        centralPanel.add(startButton, CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        darkTheme.setAlignmentX(Component.CENTER_ALIGNMENT);
        darkTheme.addActionListener(e -> setDarkTheme());
        centralPanel.add(darkTheme);
        centralPanel.add(Box.createVerticalGlue());


        Font startBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 510 / 18);
        startButton.setFont(startBtnFont);
        Font mainLabelFont = new Font("comic sans ms", Font.BOLD, 510 / 6);
        gameName.setFont(mainLabelFont);
        Font settingsBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 510 / 24);
        darkTheme.setFont(settingsBtnFont);
        if (DarkTheme){
            centralPanel.setBackground(Color.black);
            gameName.setForeground(textColor);
            startButton.setForeground(textColor);
            darkTheme.setForeground(textColor);
            darkTheme.setText("Light theme");
        }
    }
    private void setDarkTheme(){
        Color mainColor;
        String text;
        if (!DarkTheme){
            textColor = new Color(200, 200, 200);
            mainColor = Color.black;
            DarkTheme = true;
            text = "Light theme";
        }else{
            textColor = new Color(30, 30, 30);
            DarkTheme = false;
            mainColor = null;
            text = "Dark theme";
        }
        gameName.setForeground(textColor);
        startButton.setForeground(textColor);
        darkTheme.setForeground(textColor);
        darkTheme.setText(text);
        centralPanel.setBackground(mainColor);
        repaint();
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
