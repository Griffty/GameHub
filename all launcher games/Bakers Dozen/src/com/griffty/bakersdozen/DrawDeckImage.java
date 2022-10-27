package com.griffty.bakersdozen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.*;

public class DrawDeckImage {
    public static void main(String[] args) {
        String[] suits = Deck.getSUITSYMBOLS();
        String[] ranks = Deck.getRANKS();
        int cardWidth = Deck.getCARDWIDTH();
        int cardHeight = Deck.getCARDHEIGHT();



        int imageWidth = 13*cardWidth;
        int imageHeight = 4*cardHeight;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0,0,imageWidth,imageHeight);
        Font font = new Font(Font.DIALOG,Font.BOLD,24);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        for (int row = 0, y = 0; row<4;row++, y+=cardHeight){
            for (int col = 0, x =0; col<13; col++, x+=cardWidth){
                g.setColor(white);
                g.fillRoundRect(x, y,cardWidth-1,cardHeight-1, 8,8);
                g.setColor(black);
                g.drawRoundRect(x, y,cardWidth-1,cardHeight-1, 8,8);
                if (row<2){
                    g.setColor(red);
                }
                String rank = ranks[col];
                int rankWidth = fm.stringWidth(rank);
                int fromLeft = x + cardWidth/2 - rankWidth/2;
                int fromTop = y+20;
                g.drawString(rank,fromLeft,fromTop);

                String suit = suits[row];
                int suitWidth = fm.stringWidth(suit);
                fromLeft = x + cardWidth/2 - suitWidth/2;
                fromTop = y+45;
                g.drawString(suit,fromLeft,fromTop);
            }
        }

        String fileName = "cards.png";
        File file = new File(fileName);
        try {
            ImageIO.write(image, "png", file);
        }catch (IOException e){
            String message = "Image file could not be saved";
            JOptionPane.showMessageDialog(null, message);
        }

    }
}
