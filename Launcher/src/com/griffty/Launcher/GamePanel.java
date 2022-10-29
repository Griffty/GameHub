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

import static com.griffty.Launcher.Launcher.DarkTheme;

class GamePanel extends JPanel {
    private final String gameName;
    private BufferedImage image = null;
    private final int size = 295;
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
        if (DarkTheme){
            g.setColor(Color.black);
        }else {
            g.setColor(Color.lightGray);
        }
        g.fillRoundRect(0,0,size-2,size-2, 10,10);
        g.drawImage(image, 3, 3, size-8, size-8, this);
        g.setColor(Color.BLACK);
        g.drawString(gameName, (size-nameWidth)/2, size-nameHeight/2);
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
