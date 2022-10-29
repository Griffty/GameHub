package com.griffty.Launcher.Statisctic;

import com.griffty.Launcher.Statistic;

import java.util.Arrays;

public class WordBuilderStatistic extends Statistic {
    private int lettersUsed = 0;
    private String[] allWordsCreated= {};
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
