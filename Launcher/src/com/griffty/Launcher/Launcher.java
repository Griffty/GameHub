package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.griffty.Launcher.Settings.darkTheme;
import static java.awt.BorderLayout.CENTER;

public class Launcher extends JFrame {
    private Font startBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);
    private Font settingsBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, 64); 
    private Font MainLabelFont = new Font("comic sans ms", Font.BOLD, 192);
    public static Dimension windowSize;

    private static final JPanel mainPanel = new JPanel();
    private static final JPanel centralPanel = new JPanel();
    private static final JLabel gameName = new JLabel("Game Hub");
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
        mainPanel.setBackground(Color.gray);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);



        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centralPanel, CENTER);
        centralPanel.add(Box.createVerticalGlue());

        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameName.setFont(MainLabelFont);
        centralPanel.add(gameName);
        centralPanel.add(Box.createVerticalGlue());
        startButton.setFont(startBtnFont);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowSize = new Dimension(mainPanel.getWidth(), mainPanel.getHeight());
                removeAll();
                newProfileChooser();
                dispose();
            }
        });
        centralPanel.add(startButton, CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        settingsButton.setText("Settings");
        settingsButton.setFont(settingsBtnFont);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings = new Settings();
            }
        });
        centralPanel.add(settingsButton);
        centralPanel.add(Box.createVerticalGlue());
        //listeners part

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeWindow();
            }
        });
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                resizeWindow();
            }
        });
        windowSize = new Dimension(mainPanel.getWidth(), mainPanel.getHeight());
    }
    public static void newProfileChooser(){
        new profileChooser(windowSize);
    }
    private void resizeWindow(){
        int newWidth = mainPanel.getWidth();
        int newHeight = mainPanel.getHeight();

        startBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, newHeight/18);
        MainLabelFont = new Font("comic sans ms", Font.BOLD, newHeight/6);
        settingsBtnFont = new Font(Font.SANS_SERIF, Font.BOLD, newHeight/24);

        settingsButton.setFont(settingsBtnFont);
        gameName.setFont(MainLabelFont);
        startButton.setFont(startBtnFont);
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
    static void darkTheme(){
        if (darkTheme){
            mainPanel.setBackground(Color.black);
            centralPanel.setBackground(Color.black);
            gameName.setForeground(Color.lightGray);


        }
    }


}
