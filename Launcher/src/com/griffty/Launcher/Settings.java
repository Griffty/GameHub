package com.griffty.Launcher;

import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;
public class Settings extends JFrame {
    private final JPanel mainPanel = new JPanel();
    private static boolean darkThemeChecker = false;
    public static boolean darkTheme = false;
    Settings(){
        initGUI();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void initGUI(){
        mainPanel.setBackground(Color.gray);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        TitleLabel titleLabel = new TitleLabel("Settings", this);
        mainPanel.add(titleLabel, PAGE_START);
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centralPanel, CENTER);
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        centralPanel.add(settingsPanel);
        JCheckBox darkThemeCheck = new JCheckBox("Dark theme");
        darkThemeCheck.setAlignmentX(0.97f);
        darkThemeCheck.addItemListener(e -> {
            darkThemeChecker = e.getStateChange() == ItemEvent.SELECTED;
            System.out.println(darkThemeChecker);
        });
        JCheckBox someanotherchecker = new JCheckBox("some thing");
        someanotherchecker.setAlignmentX(1f);
        someanotherchecker.addItemListener(e -> {});
        settingsPanel.add(darkThemeCheck);
        settingsPanel.add(someanotherchecker);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(0.6f);
        centralPanel.add(buttonPanel);
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> saveSettings());
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

    }
    public static void saveSettings(){
        darkTheme = darkThemeChecker;
        Launcher.darkTheme();
    }
}
