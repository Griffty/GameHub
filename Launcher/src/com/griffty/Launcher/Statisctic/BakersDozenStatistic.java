package com.griffty.Launcher.Statisctic;
import com.griffty.Launcher.Statistic;
public class BakersDozenStatistic extends Statistic{
    private int CardsMoved = 0;
    public int getCardsMoved() {
        return CardsMoved;
    }
    public void addCardsMoved() {
        CardsMoved++;
    }
    public void setCardsMoved(int cardsMoved) {
        CardsMoved = cardsMoved;
    }
}
