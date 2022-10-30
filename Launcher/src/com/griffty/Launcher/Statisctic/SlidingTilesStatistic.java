package com.griffty.Launcher.Statisctic;
import com.griffty.Launcher.Statistic;
public class SlidingTilesStatistic extends Statistic {
    private int timesClicked = 0;
    public void addTimesClicked() {
        timesClicked++;
    }
    public int getTimesClicked() {
        return timesClicked;
    }
    public void setTimesClicked(int timesClicked) {
        this.timesClicked = timesClicked;
    }
}
