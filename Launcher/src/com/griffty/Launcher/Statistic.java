package com.griffty.Launcher;
public class Statistic {
    private int points = 0;
    private int wins = 0;
    private int attempts = 0;
    public void setPoints(int points) {
        this.points = points;
    }
    public void setAttempts(int attempts) {this.attempts = attempts;}
    public void setWins(int wins) {this.wins = wins;}
    public void addWin(){
        wins++;
    }
    public void addAttempt(){
        attempts++;
    }
    public int getPoints() {
        return points;
    }
    public int getAttempts() {
        return attempts;
    }
    public int getWins() {
        return wins;
    }
}
