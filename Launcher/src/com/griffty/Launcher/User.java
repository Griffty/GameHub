package com.griffty.Launcher;


import com.griffty.Launcher.Statisctic.*;

import javax.swing.*;
import java.awt.*;


public class User extends JFrame {
    private  String name = "a";
    private  String TAG = "0";
    private  String pass = "b";
    private int index;
    private final Dimension windowSize;
    public BakersDozenStatistic bakersDozenStatistic;
    public FramedStatistic framedStatistic;
    public MazeStatistic mazeStatistic;
    public SlidingTilesStatistic slidingTitlesStatistic;
    public WatchYourStepStatistic watchYourStepStatistic;
    public Statistic wizardYesOrNoStatistic;
    public WordBuilderStatistic wordBuilderStatistic;
    private CreateUser createUser;


    User(Dimension windowSize, int index){
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
    private void createUser(){CreateUser createUser = new CreateUser(windowSize, this);}


    private void newUser(String username){
        TAG = "1000";
        name = username;
        pass = null;
    }


    private void createStatistic(){
       bakersDozenStatistic = new BakersDozenStatistic();
       framedStatistic = new FramedStatistic();
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

    public CreateUser getCreateUser() {return createUser;}
}
