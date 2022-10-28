package com.griffty.bakersdozen;

import com.griffty.Launcher.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serial;

import static java.awt.Color.green;
import static javax.swing.JOptionPane.YES_OPTION;

public class TablePanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int CARDWIDTH = Deck.getCARDWIDTH();
    private static final int CARDHEIGHT = Deck.getCARDHEIGHT();
    private static final int SPACING = 4;
    private static final int MARGIN = 10;

    private static final int WIDTH = CARDWIDTH*13+SPACING*12+MARGIN*2;
    private static final int HEIGHT = CARDHEIGHT*9+MARGIN*3;
    private static final int FOUNDATIONX = WIDTH/2-(CARDWIDTH*4+SPACING*3)/2;
    private static final int FOUNDATIONY = MARGIN;
    private static final int BOARDX = MARGIN;
    private static final int BOARDY = CARDHEIGHT+MARGIN*2;
    private static final int OVERLAP = (int) (CARDHEIGHT*.65);
    private Deck deck;
    private final Deck savedDeck = new Deck();
    private final CardStack[] foundation = new CardStack[4];
    private final CardStack[] column = new CardStack[13];

    private Card movingCard;
    private int mouseX = 0;
    private int mouseY = 0;
    private int fromCol = 0;
    private final User user;

    TablePanel(User user){
        this.user = user;
        int x = FOUNDATIONX;
        int y = FOUNDATIONY;
        for(int i = 0; i<4; i++){
            foundation[i] = new CardStack(x, y, 0);
            x+=CARDWIDTH+SPACING;
        }
        x = BOARDX;
        y = BOARDY;
        for(int i = 0; i<13; i++){
            column[i] = new CardStack(x, y, OVERLAP);
            x+= CARDWIDTH+SPACING;
        }
        newGame();

        //listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                clicked(x, y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                dragged(x, y);
            }
        });
    }
    private void deal(){
        for (int i = 0; i<4; i++){
            foundation[i].clear();
        }
        for (int i = 0; i<13; i++){
            column[i].clear();
        }
        for (int row = 0; row<4; row++){
            for (int col = 0; col<13; col++){
                Card card = deck.deal();
                if (card.getValue()==12){
                    column[col].addToBeginning(card);
                }else {
                    column[col].add(card);
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // draw background
        g.setColor(green);
        g.fillRect(0,0,WIDTH,HEIGHT);
    // draw foundation
        for (int i = 0; i<4; i++){
            if (foundation[i].size() > 0){
                foundation[i].draw(g);
            }else {
                int x = foundation[i].getX();
                int y = foundation[i].getY();
                Card.drawOutline(x,y,g);
            }
        }
    // draw board
        for (int i = 0; i< 13; i++){
            column[i].draw(g);
        }
    // draw moving card
        if (movingCard!=null){
            movingCard.draw(g);
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void clicked(int x, int y){
        movingCard = null;
        for (int col = 0; col < 13 && movingCard==null; col++){
            if (column[col].size()>0){
                Card card = column[col].getLast();
                if (card.contains(x,y)){
                    movingCard = card;
                    mouseX = x;
                    mouseY = y;
                    column[col].removeLast();
                    fromCol = col;
                }
            }
        }
    }
    private void dragged(int x, int y){
        if (movingCard!=null){
            int changeX = x - mouseX;
            int changeY = y - mouseY;
            movingCard.addToXY(changeX, changeY);
            mouseX = x;
            mouseY = y;
            repaint();
        }
    }
    private void released(){
        if (movingCard!=null){
            boolean validMove = false;
            for (int i = 0; i<4 && !validMove; i++){
                int foundationX = foundation[i].getX();
                int foundationY = foundation[i].getY();
                if (movingCard.isNear(foundationX,foundationY)){
                    if (foundation[i].size()<1){
                        if (movingCard.getValue() == 0){
                            validMove = true;
                            foundation[i].add(movingCard);
                            user.bakersDozenStatistic.addCardsMoved();
                            movingCard = null;
                        }
                    }else {
                        Card topCard = foundation[i].getLast();
                        if (topCard.getSuit() == movingCard.getSuit() && topCard.getValue() == movingCard.getValue()-1){
                            validMove = true;
                            foundation[i].add(movingCard);
                            user.bakersDozenStatistic.addCardsMoved();
                            isGameOver();
                            movingCard = null;
                        }
                    }
                }
            }
            for (int i = 0; i<13 && !validMove; i++){
                if (column[i].size()>0){
                    Card card = column[i].getLast();
                    if (movingCard.isNear(card) && card.getValue() == movingCard.getValue()+1){
                        validMove = true;
                        column[i].add(movingCard);
                        user.bakersDozenStatistic.addCardsMoved();
                        movingCard = null;
                    }
                }
            }
            if (!validMove){
                column[fromCol].add(movingCard);
                movingCard = null;
            }
            repaint();
        }
    }
    private void isGameOver(){
        boolean gameOver = true;
        for (int i = 0; i <4; i++){
            if (foundation[i].size()!=13){
                gameOver = false;
            }
        }

        if (gameOver) {
            user.bakersDozenStatistic.addWin();
            String message = "Do you want to play again?";
            int option = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
            if (option == YES_OPTION) {
                newGame();
            } else {
                System.exit(999);
            }
        }
    }
    public void newGame(){
        deck = new Deck();
        savedDeck.copyFrom(deck);
        deal();
    }
    public void replay(){
        deck.copyFrom(savedDeck);

        deal();
    }
}
