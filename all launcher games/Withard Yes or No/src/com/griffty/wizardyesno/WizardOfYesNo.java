package com.griffty.wizardyesno;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.griffty.Launcher.profileChooser.saveData;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

public class WizardOfYesNo extends JFrame {
    private User user;
    public WizardOfYesNo() {
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        initGui();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Wizard yes or no");
        pack();
    }
    private void initGui(){

        TitleLabel titleLabel = new TitleLabel("Wizard yes or no", this);
        JPanel centralPanel = new BollPanel();
        centralPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
                user.wizardYesOrNoStatistic.addAttempt();
            }
        });
        add(titleLabel, PAGE_START);
        add(centralPanel, CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });
    }
}