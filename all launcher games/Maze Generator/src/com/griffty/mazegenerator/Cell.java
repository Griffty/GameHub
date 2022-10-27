package com.griffty.mazegenerator;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

import static java.awt.Color.*;

public class Cell extends JPanel{
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 20;
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public int row;
    public int col;
    private final boolean[] wall = {true, true, true, true};
    private final boolean[] path = {false, false, false, false};
    private boolean current = false;
    private boolean end = false;
    private static boolean Antimaze = false;
    Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Dimension getPreferredSize(){
        return new Dimension(SIZE, SIZE);
    }

    public void paintComponent(Graphics g) {
        //background
        g.setColor(white);
        g.fillRect(0,0,SIZE,SIZE);
        g.setColor(black);
        //walls
        if (Antimaze){
            if (!isWall(TOP)){
                g.drawLine(0,0, SIZE, 0);
            }
            if (!isWall(LEFT)){
                g.drawLine(0,0,0,SIZE);
            }
        }else{
            if (isWall(TOP)){
                g.drawLine(0,0, SIZE, 0);
            }
            if (isWall(LEFT)){
               g.drawLine(0,0,0,SIZE);
            }
        }
        //patch
        g.setColor(green);
        if (path[TOP]){g.drawLine(SIZE/2,0,SIZE/2,SIZE/2);}
        if (path[RIGHT]){g.drawLine(SIZE/2,SIZE/2,SIZE,SIZE/2);}
        if (path[BOTTOM]){g.drawLine(SIZE/2,SIZE,SIZE/2,SIZE/2);}
        if (path[LEFT]){g.drawLine(0,SIZE/2,SIZE/2,SIZE/2);}
        //balls
        if (current){
            g.setColor(green);
            g.fillOval(3,3,SIZE-6,SIZE-6);
        } else if (end){
            g.setColor(red);
            g.fillOval(3,3,SIZE-6,SIZE-6);
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    public boolean isWall(int index){
        return wall[index];
    }
    public boolean hasAllWalls(){
        return isWall(TOP) && isWall(RIGHT) && isWall(BOTTOM) && isWall(LEFT);
    }
    public void removeWall(int w){
        wall[w] = false;
        repaint();
    }
    public void openTo(Cell neighbor){
        if (row< neighbor.row){
            removeWall(BOTTOM);
            neighbor.removeWall(TOP);
        }else if (row > neighbor.row){
            removeWall(TOP);
            neighbor.removeWall(BOTTOM);
        }else if(col < neighbor.col) {
            removeWall(RIGHT);
            neighbor.removeWall(LEFT);
        }else if (col > neighbor.col){
            removeWall(LEFT);
            neighbor.removeWall(RIGHT);
        }
    }

    public void addPath(int side){
        path[side] = true;
        repaint();
    }
    public void setCurrent(boolean current){
        this.current = current;
        repaint();
    }
    public void setEnd(boolean end){
        this.end = end;
        repaint();
    }
    public static void setAntiMaze(boolean antimaze){
        Antimaze = antimaze;
    }
}