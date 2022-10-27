package com.griffty.framed;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Random;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;
import static java.awt.Color.black;

public class Framed extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int GRIDSIZE = 3;
    private LightButton[][] lightButton = new LightButton[GRIDSIZE][GRIDSIZE];
    private User user;
    Framed(){
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        initGUI();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newGame();
        pack();
    }
    private void initGUI(){
        setBackground(black);
        TitleLabel titleLabel = new TitleLabel("Framed",this );
        add(titleLabel, PAGE_START);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
        add(centerPanel, CENTER);

        for (int row =0; row < GRIDSIZE; row++){
            for (int col =0; col < GRIDSIZE; col++){
            lightButton[row][col] = new LightButton(row, col);
            lightButton[row][col].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LightButton button = (LightButton) e.getSource();
                    int row = button.getRow();
                    int col = button.getCol();
                    toggleLights(row, col);
                    user.framedStatistic.addTimesClicked();
                    endGameIfDone();
                }
            });
            centerPanel.add(lightButton[row][col]);
            }
        }
    }
    private void newGame(){
        user.framedStatistic.addAttempt();
        Random rand = new Random();
        int numberOfTimes = rand.nextInt(10) + 10;
        for (int row =0; row < GRIDSIZE; row++){
            for (int col =0; col < GRIDSIZE; col++) {
                lightButton[row][col].toggle();
            }
        }
        lightButton[1][1].turnOff();
        for (int i = 0; i < numberOfTimes; i++){
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            toggleLights(row, col);
        }
    }
    private void endGameIfDone(){
        boolean done = lightButton[0][0].isLit()
                && lightButton[0][1].isLit()
                && lightButton[0][2].isLit()
                && lightButton[1][0].isLit()
                && lightButton[1][2].isLit()
                && lightButton[2][0].isLit()
                && lightButton[2][1].isLit()
                && lightButton[2][2].isLit();
        if (done){
            user.framedStatistic.addWin();
            String message = "Congratulations! You won! Do you want to play again?";
            int option = JOptionPane.showConfirmDialog(this, message, "Play again", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION){
                newGame();
            }else {
                System.exit(999);
            }
        }


    }
    private void toggleLights(int row, int col){
        if (lightButton[row][col].equals(lightButton[0][0])){
            lightButton[row][col].toggle();
            lightButton[row+1][col+1].toggle();
            lightButton[row][col+1].toggle();
            lightButton[row+1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[2][2])){
            lightButton[row][col].toggle();
            lightButton[row-1][col-1].toggle();
            lightButton[row][col-1].toggle();
            lightButton[row-1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[0][2])){
            lightButton[row][col].toggle();
            lightButton[row+1][col-1].toggle();
            lightButton[row][col-1].toggle();
            lightButton[row+1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[2][0])){
            lightButton[row][col].toggle();
            lightButton[row-1][col+1].toggle();
            lightButton[row][col+1].toggle();
            lightButton[row-1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[1][1])){
            lightButton[row][col].toggle();
            lightButton[row][col+1].toggle();
            lightButton[row][col-1].toggle();
            lightButton[row+1][col].toggle();
            lightButton[row-1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[0][1])){
            lightButton[row][col].toggle();
            lightButton[row][col+1].toggle();
            lightButton[row][col-1].toggle();
        }
        if (lightButton[row][col].equals(lightButton[2][1])){
            lightButton[row][col].toggle();
            lightButton[row][col+1].toggle();
            lightButton[row][col-1].toggle();
        }
        if (lightButton[row][col].equals(lightButton[1][0])){
            lightButton[row][col].toggle();
            lightButton[row+1][col].toggle();
            lightButton[row-1][col].toggle();
        }
        if (lightButton[row][col].equals(lightButton[1][2])){
            lightButton[row][col].toggle();
            lightButton[row+1][col].toggle();
            lightButton[row-1][col].toggle();
        }
    }

    public static void main(String[] args) {
        try {
            String classname = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(classname);
        }catch (Exception ignored) {}
        EventQueue.invokeLater(Framed::new);
    }
}
