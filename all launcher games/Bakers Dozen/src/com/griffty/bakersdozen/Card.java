package com.griffty.bakersdozen;

import java.awt.*;

import static java.awt.Color.black;

public class Card {
    private String rank = "";
    private int suit = -1;
    private int value = 0;
    private Image image = null;
    private static int width = 0;
    private static int height = 0;
    private int x = 0;
    private int y = 0;

    public Card(String rank, int suit, int value, Image image){
        this.rank=rank;
        this.suit=suit;
        this.value=value;
        this.image=image;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }


    public String getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void addToXY(int changeX, int changeY){
        y+= changeY;
        x+= changeX;
    }
    public void draw(Graphics g){
        g.drawImage(image, x ,y, null);
    }

    public static void drawOutline(int x, int y, Graphics g){
        g.setColor(black);
        g.drawRoundRect(x, y, width, height, 8, 8);
    }

    public boolean contains(int pointX, int pointY){
        boolean contains = false;
        if (pointX >= x && pointX <= x+width && pointY>= y && pointY <= y+height){
            contains = true;
        }
        return contains;
    }
    public boolean isNear(int pointX, int pointY){
        boolean isNear =false;
        int offsetX = width/2;
        int offsetY = height;
        if (pointX > x-offsetX && pointX < x +offsetX && pointY > y-offsetY && pointY < y+offsetY){
            isNear = true;
        }
        return isNear;
    }

    public boolean isNear(Card card){
        int pointX = card.getX();
        int pointY = card.getY();
        boolean isNear = isNear(pointX, pointY);
        return isNear;
    }

}
