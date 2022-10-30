package com.griffty.Launcher.Statisctic;
import com.griffty.Launcher.Statistic;
public class WatchYourStepStatistic extends Statistic {
    private int tilesOpened = 0;
    public void addTilesOpened(){
        tilesOpened++;
    }
    public int getTilesOpened() {
        return tilesOpened;
    }
    public void setTilesOpened(int tilesOpened) {
        this.tilesOpened = tilesOpened;
    }
}
