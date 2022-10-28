package com.griffty.mazegenerator;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Random;

import static java.awt.BorderLayout.*;
import static java.awt.Color.black;
import static java.awt.event.KeyEvent.*;

public class MazeGenerator extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private final TitleLabel titleLabel = new TitleLabel("Maze", this);
    private static int rows = 13, cols = 13; //all
    private int row = 0, col = 0;// current
    private int endRow = rows-1, endCol = cols-1;//exit coordinates
    private boolean alreadyWin = false;
    private Cell[][] cell = new Cell[rows][cols];
    private final JPanel mazePanel = new JPanel();
    private User user;

    public MazeGenerator() {
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        initGUI();
        setTitle("Maze Generator");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void initGUI(){
        setBackground(black);

            add(titleLabel, PAGE_START);
            //////////////////////////
            JPanel centerPanel = new JPanel();
            add(centerPanel, CENTER);
            //////////////////////////
            JPanel settingsPanel = new JPanel();
            centerPanel.add(settingsPanel);
            JMenuBar settings = new JMenuBar();
            settingsPanel.add(settings);
            JMenu menu = new JMenu();
        try {
            InputStream input = getClass().getResourceAsStream("/Settings-48.png");
            assert input != null;
            BufferedImage image = ImageIO.read(input);
            menu.setIcon(new ImageIcon(image));
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error! Make sure all necessary files and images are present and try again");

        }
            Dimension size = new Dimension(58, 58);
            menu.setPreferredSize(size);
            menu.setBackground(Color.WHITE);
            settings.add(menu);
            JMenu menuSize = new JMenu("Size");
            menu.add(menuSize);
            JMenu menuType = new JMenu("Type");
            menu.add(menuType);
            JMenuItem x5 = new JMenuItem("5x5");
            x5.addActionListener(e -> setMazeSize(5));
            menuSize.add(x5);
            JMenuItem x9 = new JMenuItem("9x9");
            x9.addActionListener(e -> setMazeSize(9));
            menuSize.add(x9);
            JMenuItem x12 = new JMenuItem("12x12");
            x12.addActionListener(e -> setMazeSize(12));
            menuSize.add(x12);
            JMenuItem x23 = new JMenuItem("23x23");
            x23.addActionListener(e -> setMazeSize(23));
            menuSize.add(x23);
            JMenuItem x35 = new JMenuItem("35x35");
            x35.addActionListener(e -> setMazeSize(35));
            menuSize.add(x35);
            JMenuItem x47 = new JMenuItem("47x47");
            x47.addActionListener(e -> setMazeSize(47));
            menuSize.add(x47);

            JMenuItem Maze = new JMenuItem("Maze");
            Maze.addActionListener(e -> changeMaze(false));
            menuType.add(Maze);
            JMenuItem AntiMaze = new JMenuItem("Antimaze");
            AntiMaze.addActionListener(e -> changeMaze(true));
            menuType.add(AntiMaze);
            ///////////////////////////
            newMaze();
            centerPanel.add(mazePanel);
            //////////////////////////
            addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    moveBall(keyCode);
                }
            });
            //////////////////////////
            JPanel buttonpanel = new JPanel();
            buttonpanel.setBackground(Color.black);
            add(buttonpanel, PAGE_END);
            JButton newGame = new JButton("New maze");
            newGame.setFocusable(false);
            newGame.addActionListener(e -> newMaze());
            buttonpanel.add(newGame);
    }
    private void setMazeSize(int size){
        rows = size;
        cols = size;
        newMaze();
        setLocationRelativeTo(null);
    }
    private void changeMaze(boolean antiMaze){
        Cell.setAntiMaze(antiMaze);
        if(antiMaze){
            titleLabel.setText("Antimaze");
        }else {
            titleLabel.setText("Maze");
        }
        newMaze();
    }

    private void moveBall(int direction){
        user.mazeStatistic.addCellPassed();
        switch (direction){
            case VK_UP:
                if (!cell[row][col].isWall(Cell.TOP)){
                    moveTo(row-1, col, Cell.TOP, Cell.BOTTOM);
                    while (!cell[row][col].isWall(Cell.TOP) &&
                            cell[row][col].isWall(Cell.LEFT) &&
                            cell[row][col].isWall(Cell.RIGHT)) {
                        moveTo(row-1, col, Cell.TOP, Cell.BOTTOM);
                        user.mazeStatistic.addCellPassed();
                    }
                }break;
            case VK_RIGHT:
                if (!cell[row][col].isWall(Cell.RIGHT)){
                    moveTo(row,col+1, Cell.RIGHT, Cell.LEFT);
                    while (!cell[row][col].isWall(Cell.RIGHT)&&
                            cell[row][col].isWall(Cell.TOP) &&
                            cell[row][col].isWall(Cell.BOTTOM)){
                        moveTo(row,col+1, Cell.RIGHT, Cell.LEFT);
                        user.mazeStatistic.addCellPassed();
                    }
                }break;
            case VK_DOWN:
                if (!cell[row][col].isWall(Cell.BOTTOM)){
                    moveTo(row+1,col, Cell.BOTTOM, Cell.TOP);
                    while (!cell[row][col].isWall(Cell.BOTTOM)&&
                            cell[row][col].isWall(Cell.RIGHT) &&
                            cell[row][col].isWall(Cell.LEFT)){
                        moveTo(row+1,col, Cell.BOTTOM, Cell.TOP);
                        user.mazeStatistic.addCellPassed();
                    }
                }break;
            case VK_LEFT:
                if (!cell[row][col].isWall(Cell.LEFT)){
                    moveTo(row,col-1, Cell.LEFT, Cell.RIGHT);
                    while (!cell[row][col].isWall(Cell.LEFT)&&
                            cell[row][col].isWall(Cell.TOP) &&
                            cell[row][col].isWall(Cell.BOTTOM)) {
                        moveTo(row,col-1, Cell.LEFT, Cell.RIGHT);
                        user.mazeStatistic.addCellPassed();
                    }
                }break;
        }
    }

    private void moveTo(int nextRow, int nextCol, int firstDirection, int secondDirection){
        cell[row][col].setCurrent(false);
        cell[row][col].addPath(firstDirection);
        row = nextRow;
        col = nextCol;
        cell[row][col].setCurrent(true);
        cell[row][col].addPath(secondDirection);
        if (cell[row][col] == cell[endRow][endCol] && !alreadyWin){
            user.mazeStatistic.addWin();
            String message = "You won, congratulations! To start new game, click button above or change game settings.";
            JOptionPane.showMessageDialog(this, message);
            alreadyWin = true;
        }
    }

    private void newMaze(){
        user.mazeStatistic.addAttempt();
        mazePanel.removeAll();
        mazePanel.setLayout(new GridLayout(rows, cols));
        cell = new Cell[rows][cols];
        for (int r = 0; r<rows; r++){
            for (int c = 0; c<cols; c++){
                cell[r][c] = new Cell(r, c);
                mazePanel.add(cell[r][c]);

            }
        }

        generateMaze();
        alreadyWin = false;
        endRow = rows-1;
        endCol = cols-1;
        row = 0;
        col = 0;
        cell[row][col].setCurrent(true);
        cell[endRow][endCol].setEnd(true);
        mazePanel.revalidate();
        pack();
    }
    private boolean isAvalible(int r, int c){
        return r < rows && c < cols && r > -1 && c > -1 && cell[r][c].hasAllWalls();
    }
    private void generateMaze(){

        ArrayList<Cell> trylLaterCell = new ArrayList<>();
        int totalCells = rows * cols;
        int visitedCells = 1;
        // start at a random cell
        Random rand = new Random();
        int r = rand.nextInt(rows);
        int c = rand.nextInt(cols);
        // while not all cells have yet been visited
        while(visitedCells<totalCells) {
            // find all neighbors with all walls intact
            ArrayList<Cell> neighbors = new ArrayList<>();
            if (isAvalible(r - 1, c)) {
                neighbors.add(cell[r - 1][c]);
            }
            if (isAvalible(r + 1, c)) {
                neighbors.add(cell[r + 1][c]);
            }
            if (isAvalible(r, c - 1)) {
                neighbors.add(cell[r][c - 1]);
            }
            if (isAvalible(r, c + 1)) {
                neighbors.add(cell[r][c + 1]);
            }
            // if one or more found
            if (neighbors.size() > 0) {
                // if more than one found, add this
                // cell to the list to try again
                if (neighbors.size() > 1){
                    trylLaterCell.add(cell[r][c]);
                }
                // pick a neighbor and remove the wall
                int pick = rand.nextInt(neighbors.size());
                Cell neighbor = neighbors.get(pick);
                cell[r][c].openTo(neighbor);
                // go to the neighbor and increment
                // the number visited
                r = neighbor.getRow();
                c = neighbor.getCol();
                visitedCells++;
            } else {
                // if none was found, go to one of the
                // cells that was saved to try later
                Cell nextCell = trylLaterCell.remove(0);
                r = nextCell.getRow();
                c = nextCell.getCol();
            }
        }

    }

}
