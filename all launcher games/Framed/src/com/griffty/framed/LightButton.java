package com.griffty.framed;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

import static java.awt.Color.black;
import static java.awt.Color.red;

public class LightButton extends JButton {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int MAXSIZE = 50;
    private int row = 0;
    private int col = 0;
    public LightButton(int row, int col){
        this.row = row;
        this.col = col;
        setBackground(black);
        Dimension size = new Dimension(MAXSIZE, MAXSIZE);
        setPreferredSize(size);
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public void turnOn(){
        setBackground(red);
    }
    public void turnOff(){setBackground(black); }
    public boolean isLit(){
        Color color = getBackground();
        return color.equals(red);
    }
    public void toggle(){
        if (isLit()){
            turnOff();
        }else{
            turnOn();
        }
    }

}
