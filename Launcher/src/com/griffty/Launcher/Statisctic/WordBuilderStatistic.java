package com.griffty.Launcher.Statisctic;

import com.griffty.Launcher.Statistic;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WordBuilderStatistic extends Statistic {
    private static final String DOCUMENTS = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+ "\\GameHub\\";
    private static final String FILENAME = "highScores.txt";
    private int lettersUsed = 0;
    private String[] allWordsCreated= {};
    public ArrayList<String> getTopTen() {
        ArrayList<String> records = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(DOCUMENTS + FILENAME));
            String s = in.readLine();
            while (!(s == null)) {
                records.add(s);
                s = in.readLine();
            }
            in.close();
        } catch (FileNotFoundException e) {
            String message = "Score file was not found";
            JOptionPane.showMessageDialog(null, message);
        } catch (IOException | NumberFormatException e) {
            String message = "Some error occurred, new height score list will be created";
            JOptionPane.showMessageDialog(null, message);
        }
        return records;
    }
    public void addLettersUsed(){
        lettersUsed++;
    }
    public void addWords(String word){
        allWordsCreated = Arrays.copyOf(allWordsCreated, allWordsCreated.length + 1);
        allWordsCreated[allWordsCreated.length - 1] = word;
    }

    public int getLettersUsed() {
        return lettersUsed;
    }

    public void setLettersUsed(int lettersUsed) {
        this.lettersUsed = lettersUsed;
    }
}
