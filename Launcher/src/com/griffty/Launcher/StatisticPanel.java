package com.griffty.Launcher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatisticPanel extends JPanel {
    private final String gameName;
    private final int size = 300;
    private final Font gameNameFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final FontMetrics gameNameFM = getFontMetrics(gameNameFont);
    private final FontMetrics statisticFM = getFontMetrics(gameNameFont);
    private final User user;

    StatisticPanel(String gameName) {
        this.gameName = gameName;
        user = profileChooser.user;
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
        g.fillRoundRect(10, 10, size - 20, size - 20, 10, 10);
        g.setColor(Color.BLACK);
        g.drawString(gameName, (size - nameWidth) / 2, nameHeight + size / 20);
        int statWidth;
        switch (gameName) {
            case "Baker's Dozen" -> {

                String attempts = "Total attempts: " + user.bakersDozenStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
                String wins = "Total wins: " + user.bakersDozenStatistic.getWins();
                statWidth = statisticFM.stringWidth(wins);
                g.drawString(wins, (size-statWidth)/2 , size*4/9);
                String cardsMoved = "Total cards moved: " + user.bakersDozenStatistic.getCardsMoved();
                statWidth = statisticFM.stringWidth(cardsMoved);
                g.drawString(cardsMoved, (size-statWidth)/2 , size*5/9);
            }
            case "Word Builder" -> {
                Font statisticFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
                String attempts = "Total uses: " + user.wordBuilderStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
                String cardsMoved = "Total letters used: " + user.wordBuilderStatistic.getLettersUsed();
                statWidth = statisticFM.stringWidth(cardsMoved);
                g.drawString(cardsMoved, (size-statWidth)/2 , size*4/9);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(Box.createVerticalGlue());
                JColoredButton topTenButton = new JColoredButton("Show top ten scores");
                topTenButton.setAlignmentX(CENTER_ALIGNMENT);
                topTenButton.setFont(statisticFont);
                topTenButton.addActionListener(e -> {
                    StringBuilder message = new StringBuilder();
                    ArrayList<String> records = user.wordBuilderStatistic.getTopTen();
                    System.out.println(records);
                    for (String record:records){
                        message.append(records.indexOf(record)+1).append(". ").append(record).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, message);
                });
                add(topTenButton);
                add(Box.createRigidArea(new Dimension(0, 15)));
            }
            case "Maze" -> {
                String attempts = "Total attempts: " + user.mazeStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
                String wins = "Total wins: " + user.mazeStatistic.getWins();
                statWidth = statisticFM.stringWidth(wins);
                g.drawString(wins, (size-statWidth)/2 , size*4/9);
                String cardsMoved = "Total cells traveled: " + user.mazeStatistic.getCellsPassed();
                statWidth = statisticFM.stringWidth(cardsMoved);
                g.drawString(cardsMoved, (size-statWidth)/2 , size*5/9);
            }
            case "Wizard Yes Or No" -> {
                String attempts = "Total uses: " + user.wizardYesOrNoStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
            }
            case "Sliding Tiles" -> {
                String attempts = "Total attempts: " + user.slidingTitlesStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
                String wins = "Total wins: " + user.slidingTitlesStatistic.getWins();
                statWidth = statisticFM.stringWidth(wins);
                g.drawString(wins, (size-statWidth)/2 , size*4/9);
                String cardsMoved = "Total times clicked: " + user.slidingTitlesStatistic.getTimesClicked();
                statWidth = statisticFM.stringWidth(cardsMoved);
                g.drawString(cardsMoved, (size-statWidth)/2 , size*5/9);
            }
            case "Watch Your Step" -> {
                String attempts = "Total attempts: " + user.watchYourStepStatistic.getAttempts();
                statWidth = statisticFM.stringWidth(attempts);
                g.drawString(attempts, (size-statWidth)/2 , size*3/9);
                String wins = "Total wins: " + user.watchYourStepStatistic.getWins();
                statWidth = statisticFM.stringWidth(wins);
                g.drawString(wins, (size-statWidth)/2 , size*4/9);
                String cardsMoved = "Total tiles opened: " + user.watchYourStepStatistic.getTilesOpened();
                statWidth = statisticFM.stringWidth(cardsMoved);
                g.drawString(cardsMoved, (size-statWidth)/2 , size*5/9);
            }
        }
    }
}
