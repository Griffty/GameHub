package com.griffty.watchyourstep;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

import static java.awt.Color.black;
import static java.awt.Color.cyan;

public class TerrainButton extends JButton {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 50;
    private final int row;
    private final int col;
    private int nextToHoles = 0;
    private boolean hole = false;
    private boolean revealed = false;
    TerrainButton(int row, int col){
        this.row=row;
        this.col=col;
        Dimension size = new Dimension(SIZE, SIZE);
        setPreferredSize(size);
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public boolean hasHole(){
        return hole;
    }
    public boolean isRevealed(){
        return revealed;
    }
    public void setHole(boolean hasHole){
        hole = hasHole;
    }
    public void increaseHoleCount(){
        nextToHoles++;
    }
    public boolean isNextToHoles(){
        return nextToHoles>0;
    }
    public void reveal(boolean reveal){
        revealed = reveal;
        if (revealed){
            if (hasHole()){
                setBackground(black);
            }else{
                setBackground(cyan);
                if (isNextToHoles()){
                    setText(nextToHoles + "");
                }
            }
        }else{
            setBackground(null);
            setText(null);
        }
    }
    public void reset(){
        hole = false;
        revealed = false;
        nextToHoles = 0;
        setText(null);
        setBackground(null);
    }
}
