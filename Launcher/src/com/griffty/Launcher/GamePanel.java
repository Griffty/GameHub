package com.griffty.Launcher;

import com.griffty.bakersdozen.BakersDozen;
import com.griffty.mazegenerator.MazeGenerator;
import com.griffty.slidingtiles.SlidingTiles;
import com.griffty.watchyourstep.WatchYourStep;
import com.griffty.wizardyesno.WizardOfYesNo;
import com.griffty.worldbuilder.WordBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

class GamePanel extends JPanel {
    private final String gameName;
    private BufferedImage image = null;
    private final int size = 300;
    private final Font gameNameFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final FontMetrics gameNameFM;

    GamePanel(String gameName){
        this.gameName = gameName;
        String FILENAME = "/" + gameName + ".png";
        try {

            InputStream input = getClass().getResourceAsStream(FILENAME);
            assert input != null;
            image = ImageIO.read(input);
        }catch (Exception e){
            System.out.println(FILENAME);
            String message = "Icon image cannot be read";
            JOptionPane.showMessageDialog(this, message);
        }
        gameNameFM = getFontMetrics(gameNameFont);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int nameWidth = gameNameFM.stringWidth(gameName);
        int nameHeight = gameNameFM.getHeight();
        g.setFont(gameNameFont);
        g.setColor(Color.lightGray);
        g.fillRoundRect(10,10,size-20,size-20, 10,10);
        g.drawImage(image, 20, 20, size-40, size-40, this);
        g.setColor(Color.BLACK);
        g.drawString(gameName, (size-nameWidth)/2, size-nameHeight);
    }

    public void startGame(){
        switch (gameName) {
            case "Baker's Dozen" -> new BakersDozen();
            case "Word Builder" -> new WordBuilder();
            case "Maze" -> new MazeGenerator();
            case "Wizard Yes Or No" -> new WizardOfYesNo();
            case "Sliding Tiles" -> new SlidingTiles();
            case "Watch Your Step" -> new WatchYourStep();
        }
    }
}
