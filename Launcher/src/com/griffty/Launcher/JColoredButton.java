package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;

import static com.griffty.Launcher.Launcher.DarkTheme;

public final class JColoredButton extends JButton{
    public JColoredButton(String text){
        super(text);
        setContentAreaFilled(false);
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        boolean dark = DarkTheme;
        if (dark) {
            g2.setColor(Color.darkGray);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
            g2.setColor(Color.black);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }else {
            g2.setColor(Color.white);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
            g2.setColor(Color.lightGray);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
