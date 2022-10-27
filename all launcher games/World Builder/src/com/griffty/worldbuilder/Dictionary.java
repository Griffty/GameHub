package com.griffty.worldbuilder;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Dictionary {
    private static final String FILENAME = "/enable1_3-15.txt";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<String>[] wordList = (ArrayList<String>[]) new ArrayList[26];

    Dictionary(){
        for (int i = 0; i<26; i++){
            wordList[i] = new ArrayList<String>();
        }
        try{
            InputStream input = getClass().getResourceAsStream(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String word = in.readLine();
            while(!(word == null)){
                char letter = word.charAt(0);
                int list = ALPHABET.indexOf(letter);
                wordList[list].add(word);
                word = in.readLine();
            }
            in.close();
        }catch (FileNotFoundException e){
            String message = "stating file was not fond";
            JOptionPane.showMessageDialog(null, message);
        }catch (IOException e){
            String message = "stating file cannot be opened";
            JOptionPane.showMessageDialog(null, message);
        }

    }
    public boolean isAWord(String word){
        boolean found = false;
        word = word.toUpperCase();
        char letter = word.charAt(0);
        int list = ALPHABET.indexOf(letter);
        int index = 0;
        String word2 = "";
        while (index < wordList[list].size() && word2.compareTo(word)<0 && !found) {
            word2 = wordList[list].get(index);
            if (word2.equals(word)){
                found = true;
            }
            index++;
        }
        return found;
    }
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        String word = "state";
        if (dictionary.isAWord(word)){
            System.out.println(word);
        }else {
            System.out.println("fail");
        }
    }
}
