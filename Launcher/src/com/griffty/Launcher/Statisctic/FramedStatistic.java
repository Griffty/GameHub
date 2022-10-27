package com.griffty.Launcher.Statisctic;

import com.griffty.Launcher.Statistic;

public class FramedStatistic extends Statistic {
    private int timesClicked = 0;

    public void addTimesClicked(){
        timesClicked++;
    }
}
