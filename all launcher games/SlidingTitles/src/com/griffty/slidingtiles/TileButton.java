package com.griffty.slidingtiles;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

import static java.awt.Color.white;

public class TileButton extends JButton {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int tileSize = 0;
    private static int maxTiles = 0;
    private ImageIcon imageIcon = new ImageIcon();
    private int imageId = 0;
    private int row = 0;
    private int col = 0;
    public void setImage(ImageIcon imageIcon, int imageId){
        this.imageIcon = imageIcon;
        this.imageId = imageId;
        if (imageId == maxTiles-1){
            setIcon(null);
        }else {
            setIcon(imageIcon);
        }
    }
    TileButton(ImageIcon imageIcon, int imageId, int row, int col){
        this.row = row;
        this.col = col;
        setImage(imageIcon, imageId);
        setBackground(white);
        setBorder(null);
        Dimension size = new Dimension(tileSize, tileSize);
        setPreferredSize(size);
        setFocusPainted(false);
    }

    public ImageIcon getImage() {return imageIcon;}
    public int getImageId() {return imageId;}
    public int getRow() {return row;}
    public int getCol() {return col;}

    public static void setTileSizeAndMaxTiles(int size, int max){
        tileSize = size;
        maxTiles = max;
    }
    public boolean hasNoImage(){
        if (getIcon() == null){return true;}
        else{ return false;}

    }
    public void swap(TileButton otherTile){
        ImageIcon otherimageIcon = otherTile.getImage();
        int otherimageId = otherTile.getImageId();
        otherTile.setImage(this.imageIcon, this.imageId);
        setImage(otherimageIcon, otherimageId);
    }
    public void showImage(){
        setIcon(imageIcon);
    }
}
