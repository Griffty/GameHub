package com.griffty.Launcher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import static com.griffty.Launcher.Launcher.DarkTheme;
import static com.griffty.Launcher.profileChooser.users;


public class CreateUser extends JFrame {
    private final User user;
    private final Dimension windowSize;
    private final Font TopFont = new Font("comic sans ms", Font.BOLD, 30);
    private final Font buttonFont = new Font("comic sans ms", Font.BOLD, 20);
    private final Font mainTextFont = new Font(Font.DIALOG, Font.BOLD, 20);
    private final JFrame frame;

    public CreateUser(Dimension windowSize, User user, JFrame frame) {
        profileChooser.setAnotherGUIOpened(true);
        this.frame=frame;
        this.user = user;
        this.windowSize = windowSize;
        initGUI();
        setTitle("Registration");
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
        JLabel TopLabel = new JLabel("Registration");
        TopLabel.setFont(TopFont);
        TopLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(TopLabel);
        mainPanel.add(Box.createVerticalGlue());
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        mainPanel.add(usernamePanel);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(mainTextFont);
        usernamePanel.add(usernameLabel);
        JTextField usernameTextField = new JTextField(10);
        usernameTextField.setMaximumSize(new Dimension(width/3, height/10));
        usernamePanel.add(usernameTextField);
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
        JColoredButton continueButton = new JColoredButton("Register");
        continueButton.addActionListener(e -> {
            Component parent = (Component) e.getSource();
            boolean valid = true;
            if (!(usernameTextField.getText().matches("[A-Za-z0-9]+") && !(usernameTextField.getText().length()<3))){
                String message = "Username must include at least 3 characters and contain at least one letter";
                JOptionPane.showMessageDialog(parent, message);
                valid = false;
            }
            if (!(passTextField.getText().matches("[A-Za-z0-9]+") && !(passTextField.getText().length()<3))){
                valid = false;
                String message = "Password must include at least 3 characters and contain at least one letter";
                JOptionPane.showMessageDialog(parent, message);
            }
            if (valid){
                newUser(usernameTextField.getText(), passTextField.getText());
                profileChooser.setAnotherGUIOpened(false);
                dispose();
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
            usernameTextField.setBackground(Color.gray);
            usernameTextField.setBorder(new LineBorder(Color.black));
            passTextField.setBackground(Color.gray);
            passTextField.setBorder(new LineBorder(Color.black));
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                profileChooser.setAnotherGUIOpened(false);
                users[user.getIndex()] = null;
                super.windowClosed(e);
            }
        });

    }
    private void newUser(String username, String password){
        Random rand = new Random();
        user.setTAG(rand.nextInt(899)+100 + "");
        user.setName(username);
        user.setPass(password);
        frame.repaint();
        profileChooser.repaintPanel();
    }
}
