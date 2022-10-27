package com.griffty.slidingtiles;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.Random;

import static java.awt.BorderLayout.*;
import static java.awt.Color.black;
import static javax.swing.JFileChooser.APPROVE_OPTION;


public class SlidingTiles extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILENAME = "/slidingTilesImage.jpg";
    private int tileSize = 50;
    private int gridSize = 4;
    private BufferedImage image = null;
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int IMAGESIZE = 200;

    private TileButton[][] tile = new TileButton[gridSize][gridSize];

    private JPanel centerPanel = new JPanel();
    private User user;
    public SlidingTiles() {
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        try{
            InputStream input = getClass().getResourceAsStream(FILENAME);
            image = ImageIO.read(input);
            TileButton.setTileSizeAndMaxTiles(tileSize, gridSize*gridSize);
            initGUI();
            setResizable(false);
            setLocationRelativeTo(null);
            pack();
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }catch (IOException e){
            String message = "The image file couldn't be opened.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
    private void initGUI(){
        setBackground(black);
        // menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu sizeMenu = new JMenu("Size");
        menuBar.add(fileMenu);
        menuBar.add(sizeMenu);
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem size3MenuItem = new JMenuItem("3x3");
        size3MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGridSize(3);
            }
        });
        JMenuItem size4MenuItem = new JMenuItem("4x4");
        size4MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGridSize(4);
            }
        });
        JMenuItem size5MenuItem = new JMenuItem("5x5");
        size5MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGridSize(5);
            }
        });
        sizeMenu.add(size3MenuItem);
        sizeMenu.add(size4MenuItem);
        sizeMenu.add(size5MenuItem);
        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
        //title
        TitleLabel titlelabel = new TitleLabel("Sliding Titles",this);
        add(titlelabel, PAGE_START);

        divideImage();

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, PAGE_END);
        buttonPanel.setBackground(black);
        JButton scrambleButton = new JButton("Scramble");
        scrambleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        buttonPanel.add(scrambleButton);
    }
    private void setGridSize(int size){
        gridSize = size;
        tileSize = IMAGESIZE / gridSize;
        TileButton.setTileSizeAndMaxTiles(tileSize, gridSize*gridSize);
        tile = new TileButton[gridSize][gridSize];
        divideImage();
        pack();
    }
    private void open(){
        JFileChooser chooser = new JFileChooser();
        ImageFileFilter fileFilter = new ImageFileFilter();
        chooser.setFileFilter(fileFilter);
        int option = chooser.showOpenDialog(this);
        if(option == APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            try {
                BufferedImage newImage = ImageIO.read(file);
                int width = newImage.getWidth();
                int height = newImage.getHeight();
                if(width<height){
                    height = width;
                } else if (width>height){
                    width=height;
                }
                Graphics g = image.getGraphics();
                g.drawImage(newImage, 0, 0, IMAGESIZE, IMAGESIZE,
                        0,0, width, height,this);
                g.dispose();
                divideImage();
            } catch (IOException e) {
                String message = "file " + file.getPath() + " could not be opened";
                JOptionPane.showMessageDialog(this, message);
            }
        }
    }
    private void divideImage(){
        centerPanel.setLayout(new GridLayout(gridSize, gridSize));
        add(centerPanel, CENTER);
        centerPanel.removeAll();
        int imageId = 0;
        for (int row = 0; row<gridSize; row++){
            for (int col = 0; col<gridSize; col++){
                int x = col*tileSize;
                int y = row*tileSize;
                BufferedImage subimage = image.getSubimage(x, y, tileSize, tileSize);
                ImageIcon image = new ImageIcon(subimage);
                tile[row][col] = new TileButton(image, imageId, row, col);
                tile[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TileButton button = (TileButton) e.getSource();
                        clickedTile(button);
                    }
                });
                centerPanel.add(tile[row][col]);
                imageId++;
            }
        }
        centerPanel.revalidate();
        scramble();
    }
    private void clickedTile(TileButton clickedTile){
        user.slidingTitlesStatistic.addTimesClicked();
        int row = clickedTile.getRow();
        int col = clickedTile.getCol();

        if(row > 0  && tile[row-1][col].hasNoImage()){
           clickedTile.swap(tile[row-1][col]);
        }else if(row < gridSize-1 && tile[row+1][col].hasNoImage()) {
            clickedTile.swap(tile[row+1][col]);
        }else if(col > 0 && tile[row][col-1].hasNoImage()) {
            clickedTile.swap(tile[row][col-1]);
        }else if(col < gridSize-1 && tile[row][col+1].hasNoImage()) {
            clickedTile.swap(tile[row][col+1]);
        }
        if (imagesInOrder()){
            user.slidingTitlesStatistic.addWin();
            tile[gridSize-1][gridSize-1].showImage();
        };
    }
    private void scramble(){
        user.slidingTitlesStatistic.addAttempt();
        Random rand = new Random();
        int OpenRow = gridSize - 1;
        int OpenCol = gridSize - 1;
        for (int i = 0; i < 25*gridSize; i++){
            int direction = rand.nextInt(4);
            switch (direction){
                case UP:
                    if (OpenRow != 0){
                        tile[OpenRow][OpenCol].swap(tile[OpenRow-1][OpenCol]);
                        OpenRow--;
                    }break;
                case DOWN:
                    if (OpenRow != gridSize-1){
                        tile[OpenRow][OpenCol].swap(tile[OpenRow+1][OpenCol]);
                        OpenRow++;
                    }break;
                case RIGHT:
                    if (OpenCol != 0){
                        tile[OpenRow][OpenCol].swap(tile[OpenRow][OpenCol-1]);
                        OpenCol--;
                    }break;
                case LEFT:
                    if (OpenCol != gridSize-1){
                        tile[OpenRow][OpenCol].swap(tile[OpenRow][OpenCol+1]);
                        OpenCol++;
                    }break;


            }
        }

    }
    private boolean imagesInOrder(){
        int id = 0;
        boolean inOrder = true;
        for(int row = 0; row<gridSize && inOrder; row++){
            for (int col = 0; col<gridSize && inOrder; col++){
                int currentId = tile[row][col].getImageId();
                if (currentId != id) {
                    inOrder=false;
                }
                id++;
            }
        }
        return inOrder;
    }

    private void newGame(){
        int imageId = 0;
        for (int row = 0; row<gridSize; row++){
            for (int col = 0; col<gridSize; col++){
                int x = col*tileSize;
                int y = row*tileSize;
                BufferedImage subimage = image.getSubimage(x, y, tileSize, tileSize);
                ImageIcon image = new ImageIcon(subimage);
                tile[row][col].setImage(image, imageId);
                imageId++;
            }
        }
        scramble();
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                String className = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(className);
                new SlidingTiles();
            } catch (Exception ignored) {}
        });
    }
}