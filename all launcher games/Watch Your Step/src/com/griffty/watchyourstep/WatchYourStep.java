package com.griffty.watchyourstep;

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
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class WatchYourStep extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int GRIDSIZE = 12;
    private static final int NUMBEROFHOLES = 12;
    TerrainButton[][] terrain = new TerrainButton[GRIDSIZE][GRIDSIZE];
    private int totalRevealed = 0;
    private User user;
    public WatchYourStep() {
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        InitGUI();
        setHoles();
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Watch Your Step");

    }
    private void InitGUI(){
        setBackground(black);
        TitleLabel titleLabel = new TitleLabel("Watch your step",this);
        add(titleLabel, PAGE_START);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
        add(centerPanel, CENTER);
        for(int row = 0; row < GRIDSIZE; row++){
            for (int col = 0; col < GRIDSIZE; col++){
                terrain[row][col] = new TerrainButton(row, col);
                terrain[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TerrainButton button = (TerrainButton) e.getSource();
                        int row = button.getRow();
                        int col = button.getCol();
                        clickTerrain(row,col);
                    }
                });
                centerPanel.add(terrain[row][col]);
            }
        }
    }
    private void clickTerrain(int row, int col) {
        user.watchYourStepStatistic.addTilesOpened();
        if (terrain[row][col].hasHole()) {
            String message = "Game over. Want try again?";
            promtToNewGame(message);
        } else {
            check(row, col);
            checkNeighbors(row, col);
            if ((GRIDSIZE*GRIDSIZE)-NUMBEROFHOLES == totalRevealed){
                user.watchYourStepStatistic.addWin();
                String message = "You win. Try again?";
                int option = JOptionPane.showConfirmDialog(this, message, "Try again?", YES_NO_OPTION);
                if( option == YES_OPTION){
                    newGame();
                }
                else {
                    System.exit(999);
                }
            }
        }
    }
    private void check(int row, int col){
        if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE && !terrain[row][col].hasHole() && !terrain[row][col].isRevealed()){
            terrain[row][col].reveal(true);
            totalRevealed++;
            user.watchYourStepStatistic.addTilesOpened();
            if (!terrain[row][col].isNextToHoles()){
                checkNeighbors(row, col);
            }
        }
    }
    private void checkNeighbors(int row, int col){
        check(row-1,col-1);
        check(row,col-1);
        check(row+1,col-1);
        check(row+1 ,col);
        check(row+1 ,col+1);
        check(row ,col+1);
        check(row-1 ,col+1);
        check(row-1 ,col);
    }
    private void setHoles(){
        Random rand = new Random();
        for( int i = 0; i<NUMBEROFHOLES; i++){
            int pickRow = rand.nextInt(GRIDSIZE);
            int pickCol = rand.nextInt(GRIDSIZE);
            while (terrain[pickRow][pickCol].hasHole()){
                pickRow = rand.nextInt(GRIDSIZE);
                pickCol = rand.nextInt(GRIDSIZE);
            }
            terrain[pickRow][pickCol].setHole(true);
            addToNeighborsHoleCount(pickRow, pickCol);
        }
    }
    private void addToNeighborsHoleCount(int row, int col){
        addToHoleCount(row-1,col-1);
        addToHoleCount(row,col-1);
        addToHoleCount(row+1,col-1);
        addToHoleCount(row+1 ,col);
        addToHoleCount(row+1 ,col+1);
        addToHoleCount(row ,col+1);
        addToHoleCount(row-1 ,col+1);
        addToHoleCount(row-1 ,col);
    }
    private void addToHoleCount(int row, int col){
        if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE){
            terrain[row][col].increaseHoleCount();
        }
    }
    private void promtToNewGame(String message){
        int option = JOptionPane.showConfirmDialog(this, message, "Try again?", YES_NO_OPTION);
        if( option == YES_OPTION){
            newGame();
        }
        else {
            System.exit(999);
        }
    }
    private void newGame(){
        user.watchYourStepStatistic.addAttempt();
        for(int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                terrain[row][col].reset();
            }
        }
        setHoles();
    }
}