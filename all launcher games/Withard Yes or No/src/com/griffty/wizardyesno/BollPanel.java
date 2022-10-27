package com.griffty.wizardyesno;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.Color.black;

public class BollPanel extends JPanel {
    private Random rand = new Random();
    private Font font;
    private FontMetrics fontMetrics;
    private int size = 400;
    private String phrase = "";
    public static final String[] Answers = {"Yes", "No", "Maybe", "You can try", "Definitely no", "Definitely yes", "Most likely", "Very doubtful"};

    BollPanel(){
        font = new Font("comic sans ms", Font.BOLD, size/10);
        fontMetrics = getFontMetrics(font);

    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(new Color(67, 152, 173));
        g.fillOval(0,0, size, size);
        g.setColor(new Color(63, 142, 165));
        g.fillOval(size/6, size/6, size-size/3, size-size/3);
        g.setColor(new Color(103, 55, 0));
        g.fillRoundRect(0, size-size/10, size, size/10, 5, 5);
        g.setFont(font);
        g.setColor(black);
        phrase = Answers[rand.nextInt(Answers.length)];
        int width = fontMetrics.stringWidth(phrase);
        g.drawString(phrase, (size-width)/2, size/2);
    }
}
