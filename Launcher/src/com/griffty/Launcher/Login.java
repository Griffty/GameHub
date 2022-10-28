package com.griffty.Launcher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.griffty.Launcher.Launcher.DarkTheme;

public class Login extends JFrame {
    private final User user;
    private final Dimension windowSize;
    private final Font TopFont = new Font("comic sans ms", Font.BOLD, 30);
    private final Font buttonFont = new Font("comic sans ms", Font.BOLD, 20);
    private final Font mainTextFont = new Font(Font.DIALOG, Font.BOLD, 20);
    private final JFrame PROFILECHOOSER;
    public Login(Dimension windowSize, User user, JFrame PROFILECHOOSER) {
        profileChooser.setAnotherGUIOpened(true);
        this.PROFILECHOOSER = PROFILECHOOSER;
        this.user = user;
        this.windowSize = windowSize;
        initGUI();
        setTitle("Sign in");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initGUI(){
        JPanel mainPanel = new JPanel();
        int width = (int) windowSize.getWidth();
        int height = (int) windowSize.getHeight();
        mainPanel.setPreferredSize(new Dimension(width/2, height/2));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);
        JLabel TopLabel = new JLabel("Sign in");
        TopLabel.setFont(TopFont);
        TopLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(TopLabel);
        mainPanel.add(Box.createVerticalGlue());
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        mainPanel.add(usernamePanel);
        JLabel usernameLabel = new JLabel("Username: " + user.getName());
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(mainTextFont);
        usernamePanel.add(usernameLabel);
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passPanel);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(mainTextFont);
        passPanel.add(passLabel);
        JTextField passTextField = new JTextField();
        passTextField.setMaximumSize(new Dimension(width/3, height/10));
        passPanel.add(passTextField);
        JColoredButton continueButton = new JColoredButton("Continue");
        continueButton.addActionListener(e -> {
            Component parent = (Component) e.getSource();
            if (passTextField.getText().equals(user.getPass())){
                new GameMenu();
                profileChooser.setAnotherGUIOpened(false);
                PROFILECHOOSER.dispose();
                dispose();
            }else {
                String message = "Wrong pass, try again";
                JOptionPane.showMessageDialog(parent, message);
            }

        });
        continueButton.setFont(buttonFont);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(continueButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (DarkTheme){
            mainPanel.setBackground(Color.darkGray);
            passPanel.setBackground(Color.darkGray);
            usernamePanel.setBackground(Color.darkGray);

            passTextField.setBackground(Color.gray);
            passTextField.setBorder(new LineBorder(Color.black));
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                profileChooser.setAnotherGUIOpened(false);
                super.windowClosed(e);
            }
        });

    }
}