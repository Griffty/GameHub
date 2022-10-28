package com.griffty.worldbuilder;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;
import static java.awt.Color.BLACK;
import static java.awt.Color.black;
import static javax.swing.JOptionPane.YES_OPTION;

public class WordBuilder extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int ROWS = 8, COLS = 12, MAX = 15;

    private static final String FILENAME = "highScores.txt";

    private static final Color TAN = new Color(222, 191, 168);
    private static final Font SMALLFONT = new Font(Font.DIALOG, Font.BOLD, 12);
    private static final Font BIGFONT = new Font(Font.DIALOG, Font.BOLD, 36);

    private final LetterPanel[][] board = new LetterPanel[ROWS][COLS];
    private final LetterPanel[] played = new LetterPanel[MAX];
    private int points = 0, score = 0;
    private String word = "";
    private final Dictionary dictionary = new Dictionary();
    private final JPanel mainPanel = new JPanel();
    private final JPanel boardPanel = new JPanel();
    private final JPanel scorePanel = new JPanel();
    private final JPanel playPanel = new JPanel();
    private final JLabel pointsTitleLabel = new JLabel("Points: ");
    private final JLabel scoreTitleLabel = new JLabel("Score: ");
    private final JLabel pointsLabel = new JLabel("0");
    private final JLabel scoreLabel = new JLabel("0");

    private final JButton acceptButton = new JButton("Accept");
    private final JButton undoButton = new JButton("Undo");
    private final JButton clearButton = new JButton("Clear");
    private User user;

    public WordBuilder(){
        user = profileChooser.user;
        if (user == null){
            user = new User("guest");
        }
        initGUI();
        setTitle("World Builder");
        setVisible(true);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.NORMAL);

    }
    private void initGUI(){
        user.wordBuilderStatistic.addAttempt();
        TitleLabel titleLabel = new TitleLabel("Word Builder",this);
        add(titleLabel, PAGE_START);
        //main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(TAN);
        add(mainPanel, CENTER);


        //score panel
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
        scorePanel.setBackground(TAN);
        mainPanel.add(scorePanel);
        pointsTitleLabel.setFont(SMALLFONT);
        scorePanel.add(pointsTitleLabel);
        pointsLabel.setFont(BIGFONT);
        scorePanel.add(pointsLabel);
        Dimension boxSize = new Dimension(20, 0);
        Component box = Box.createRigidArea(boxSize);
        scorePanel.add(box);
        scoreTitleLabel.setFont(SMALLFONT);
        scorePanel.add(scoreTitleLabel);
        scoreLabel.setFont(BIGFONT);
        scorePanel.add(scoreLabel);
        // play panel
        playPanel.setLayout(new GridLayout(1, MAX));
        playPanel.setBackground(TAN);
        mainPanel.add(playPanel);
        for (int i = 0; i < MAX; i++){
            LetterPanel letterPanel = new LetterPanel();
            played[i] = letterPanel;
            playPanel.add(letterPanel);
        }
        mainPanel.add(Box.createGlue());
        // board panel
        boardPanel.setBackground(black);
        boardPanel.setLayout(new GridLayout(ROWS, COLS));
        int panelSize = played[0].getPanelSize();
        Dimension maxSize = new Dimension(COLS*panelSize, ROWS*panelSize);
        boardPanel.setMaximumSize(maxSize);
        mainPanel.add(boardPanel);
        BagOfLetters letters = new BagOfLetters();
        for (int row = 0; row<ROWS;row++){
            for (int col = 0; col<COLS;col++){
                LetterPanel letterPanel = letters.pickALatter();
                letterPanel.setColumn(col);
                board[row][col] = letterPanel;
                boardPanel.add(board[row][col]);
            }
        }
        for (int col = 0; col<COLS; col++){
            board[0][col].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    user.wordBuilderStatistic.addLettersUsed();
                    LetterPanel letterPanel =(LetterPanel) e.getSource();
                    click(letterPanel);
                }
            });
        }
        mainPanel.add(Box.createGlue());
        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BLACK);
        mainPanel.add(buttonPanel);
        acceptButton.setEnabled(false);
        acceptButton.addActionListener(e -> {
            user.wordBuilderStatistic.addWords(word);
            accept();
        });
        buttonPanel.add(acceptButton);
        undoButton.setEnabled(false);
        undoButton.addActionListener(e -> undo());
        buttonPanel.add(undoButton);
        clearButton.setEnabled(false);
        clearButton.addActionListener(e -> clear());
        buttonPanel.add(clearButton);
        JButton endButton = new JButton("End Game");
        endButton.addActionListener(e -> endGame());
        buttonPanel.add(endButton);
        // listeners
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeWindow();
            }
        });
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                resizeWindow();
            }
        });
    }

    private void click(LetterPanel letterPanel){
        int wordLength = word.length();
        if (!letterPanel.isEmpty() && word.length()<MAX){
            played[wordLength].copy(letterPanel);
            word += letterPanel.getLetter();
            points += letterPanel.getPoints();
        }
        int col = letterPanel.getColumn();
        for (int row = 0; row<ROWS-1; row++){
            board[row][col].copy(board[row+1][col]);
        }
        board[ROWS-1][col].setEmpty();
        updateButtonsAndPoints();
    }
    private void updateButtonsAndPoints(){
        if (word.length() == 0){
            acceptButton.setEnabled(false);
            undoButton.setEnabled(false);
            clearButton.setEnabled(false);
            pointsLabel.setText("0");
        }else if (word.length() < 3){
            acceptButton.setEnabled(false);
            undoButton.setEnabled(true);
            clearButton.setEnabled(true);
            pointsLabel.setText("0");
        }else {
            acceptButton.setEnabled(dictionary.isAWord(word));
            undoButton.setEnabled(true);
            clearButton.setEnabled(true);
            int newPoints = points*word.length();
            pointsLabel.setText(newPoints+"");

        }
    }

    private void accept(){
        score = score + points*word.length();
        scoreLabel.setText(score+"");
        for (int i = 0; i < word.length(); i++){
            played[i].setEmpty();
        }
        points = 0;
        word = "";
        updateButtonsAndPoints();
    }
    private void undo(){
        int last = word.length()-1;
        word = word.substring(0,last);
        LetterPanel lastPlayedPanel = played[last];
        points -= lastPlayedPanel.getPoints();
        int col = lastPlayedPanel.getColumn();
        for (int row = ROWS-1; row>0; row--){
            board[row][col].copy(board[row-1][col]);
        }
        board[0][col].copy(lastPlayedPanel);
        lastPlayedPanel.setEmpty();
        updateButtonsAndPoints();
    }
    private void clear(){
        int numberOfTimes = word.length();
        for (int i = 0; i<numberOfTimes; i++){
            undo();
        }
    }
    private void endGame(){
        ArrayList<String> records = new ArrayList<>();
        int index = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(FILENAME));
            String s = in.readLine();
            while (!(s==null)){
                records.add(s);
                int indexOfBlank = s.indexOf(" ");
                String scoreString = s.substring(0, indexOfBlank);
                int oldScore = Integer.parseInt(scoreString);
                if (oldScore>score){
                    index++;
                }
                s = in.readLine();
            }
            in.close();
        }catch (FileNotFoundException e){
            String message = "Score file was not found";
            JOptionPane.showMessageDialog(this, message);
        }catch (IOException | NumberFormatException e){
            String message = "Some error occurred, new height score list will be created";
            JOptionPane.showMessageDialog(this, message);
        }
        StringBuilder message = new StringBuilder();
        if (index<10){
            message = new StringBuilder("Hooray! Your score is one of the top 10.");
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String newRecord = score + " " + dateFormat.format(date);
            records.add(newRecord);
            if (records.size()>10){
                records.remove(9);
            }
            saveRecord(records);

        }
        message.append("TOP 10 HIGH SCORES: \n ");
        for (String record : records) {
            message.append(record).append("\n");
        }
        message.append("Do you want to play again?");
        int choice = JOptionPane.showConfirmDialog(this, message.toString(), "Play again?",  JOptionPane.YES_NO_OPTION);
        if (choice == YES_OPTION){
            newGame();
        }else{
            System.exit(999);
        }
    }
    private void newGame(){
        clear();
        BagOfLetters letters = new BagOfLetters();
        for (int row = 0; row<ROWS;row++){
            for (int col = 0; col<COLS;col++){
                LetterPanel letterPanel = letters.pickALatter();
                letterPanel.setColumn(col);
                board[row][col].copy(letterPanel);
            }
        }
        score = 0;
        points = 0;
        word = "";
        scoreLabel.setText(score+"");
        updateButtonsAndPoints();
    }
    private void saveRecord(ArrayList<String> records){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            for (String record : records) {
                out.write(record);
                out.newLine();
            }
            out.close();
        }catch (IOException e){
            String message = "High score records could not be saved";
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void resizeWindow(){
        int newWidth = mainPanel.getWidth();
        int newHeight = mainPanel.getHeight();
        int panelSize = newWidth/MAX;
        if (panelSize>newHeight/10){
            panelSize = newHeight/10;
        }
        Dimension boarSize = new Dimension(COLS*panelSize, ROWS*panelSize);
        boardPanel.setMaximumSize(boarSize);
        for (int row = 0; row<ROWS;row++){
            for (int col = 0; col<COLS;col++){
                board[row][col].resize(panelSize);
            }
        }
        Dimension playSize = new Dimension(MAX*panelSize, panelSize);
        playPanel.setMaximumSize(playSize);
        for (int row = 0; row<MAX;row++){
                played[row].resize(panelSize);
        }
        Font bigFont = new Font(Font.DIALOG, Font.BOLD, panelSize*3/4);
        Font smallFont = new Font(Font.DIALOG, Font.BOLD, panelSize*3/10);
        pointsTitleLabel.setFont(smallFont);
        pointsLabel.setFont(bigFont);
        scoreTitleLabel.setFont(smallFont);
        scoreLabel.setFont(bigFont);
    }


}
