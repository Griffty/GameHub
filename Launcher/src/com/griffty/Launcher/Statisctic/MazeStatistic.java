package com.griffty.Launcher.Statisctic;

import com.griffty.Launcher.Statistic;

public class MazeStatistic extends Statistic {
    int cellsPassed = 0;
    public void addCellPassed(){
        cellsPassed++;
    }
    public int getCellsPassed() {
        return cellsPassed;
    }

    public void setCellsPassed(int cellsPassed) {
        this.cellsPassed = cellsPassed;
    }
}
