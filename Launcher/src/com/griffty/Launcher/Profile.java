package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class Profile extends JPanel {
    private final Dimension windowSize;
    private int size;
    private final int index;
    private  User user = null;
    private String name = "";
    private String TAG = "-1";
    private final Font nameFont = new Font("comic sans ms", Font.BOLD, 30);
    private final FontMetrics nameFM;

    Profile(Dimension windowSize, int i){

        this.windowSize = windowSize;
        this.index = i;
        nameFM = getFontMetrics(nameFont);
        setBackground(black);
    }

    @Override
    public Dimension getPreferredSize() {
        int height = (int) windowSize.getHeight();
        int width = (int) windowSize.getWidth();
        size = Math.min(height, width)/2;

        return new Dimension(size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int boxSize = size+23;
        if (user==null){
            g.setColor(lightGray);
            g.fillRoundRect(0, 0, boxSize, boxSize, 30, 30);
            g.setColor(black);
            g.drawRoundRect(0, 0, boxSize - 1, boxSize - 1, 30, 30);
            g.setColor(gray);
            g.fillRoundRect(boxSize/2 - boxSize/32, boxSize/4, boxSize/16, boxSize - boxSize/2, 5, 10);
            g.fillRoundRect(boxSize/4, boxSize/2 - boxSize/32, boxSize - boxSize/2, boxSize/16,5, 10);
        }else {
            g.setColor(lightGray);
            g.fillRoundRect(0, 0, boxSize, boxSize, 30, 30);
            g.setColor(black);
            g.drawRoundRect(0, 0, boxSize - 1, boxSize - 1, 30, 30);
            String tag = "#" + TAG;
            int nameWidth = nameFM.stringWidth(name);
            g.setFont(nameFont);
            g.drawString(name, (boxSize - nameWidth) / 2, boxSize / 3);
            int tagWidth = nameFM.stringWidth(tag);
            int tagHieght = nameFM.getHeight();
            g.drawString(tag, (boxSize - tagWidth) / 2, (boxSize / 3)+(tagHieght));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user!=null){
            name = user.getName();
            TAG = user.getTAG();
        }
    }

    public boolean hasUser(){
        return user != null;
    }

    public int getIndex() {
        return index;
    }
}
