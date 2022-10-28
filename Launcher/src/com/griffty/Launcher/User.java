package com.griffty.Launcher;


import com.griffty.Launcher.Statisctic.*;

import javax.swing.*;
import java.awt.*;


public class User extends JFrame {
    private  String name = "";
    private  String TAG = "-1";
    private  String pass = "";
    private int index;
    private final Dimension windowSize;
    public BakersDozenStatistic bakersDozenStatistic;
    public MazeStatistic mazeStatistic;
    public SlidingTilesStatistic slidingTitlesStatistic;
    public WatchYourStepStatistic watchYourStepStatistic;
    public Statistic wizardYesOrNoStatistic;
    public WordBuilderStatistic wordBuilderStatistic;
    private JFrame jFrame;


    User(Dimension windowSize, int index, JFrame jFrame){
        this.jFrame = jFrame;
        this.index = index;
        this.windowSize = windowSize;
        createUser();
        createStatistic();
    }
    User(int index){
        this.index = index;
        windowSize = null;
        createStatistic();
    }

    public User(String guest){
        createStatistic();
        newUser(guest);
        windowSize = null;
    }
    private void createUser(){
        new CreateUser(windowSize, this, jFrame);
    }


    private void newUser(String username){
        TAG = "1000";
        name = username;
        pass = null;
    }


    private void createStatistic(){
       bakersDozenStatistic = new BakersDozenStatistic();
       mazeStatistic = new MazeStatistic();
       slidingTitlesStatistic = new SlidingTilesStatistic();
       watchYourStepStatistic = new WatchYourStepStatistic();
       wizardYesOrNoStatistic = new Statistic();
       wordBuilderStatistic = new WordBuilderStatistic();
    }

    public void setIndex(int index){this.index = index;}
    public void setTAG(String  TAG) {this.TAG = TAG;}
    public void setName(String name) {
        this.name = name;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIndex() {return index;}
    public String getName() {
        return name;
    }
    public String getTAG() {
        return TAG;
    }
    public String getPass() {
        return pass;
    }

}
