package com.griffty.worldbuilder;

import java.util.ArrayList;
import java.util.Random;

public class BagOfLetters {
    private ArrayList<LetterPanel> letterPanels = new ArrayList<LetterPanel>();
    private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] quantity = {8, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 1, 2, 1, 2, 1};
    private int[] points = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private Random rand = new Random();
    BagOfLetters(){
        for (int i = 0; i < letters.length(); i++){
            char letter = letters.charAt(i);
            for (int count = 0; count<quantity[i]; count++){
                LetterPanel letterPanel = new LetterPanel(letter+"", points[i]);
                letterPanels.add(letterPanel);
            }
        }
    }
    public LetterPanel pickALatter(){
        LetterPanel letterPanel = null;
        int size = letterPanels.size();
        if (size>0){
            int pick = rand.nextInt(size);
            letterPanel = letterPanels.remove(pick);
        }
        return letterPanel;
    }
}
