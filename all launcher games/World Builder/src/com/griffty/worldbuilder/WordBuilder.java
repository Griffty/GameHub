package com.griffty.worldbuilder;

import com.griffty.Launcher.User;
import com.griffty.Launcher.profileChooser;
import griffty.TitleLabel;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.griffty.Launcher.profileChooser.gameMenuStatic;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;
import static java.awt.Color.BLACK;
import static java.awt.Color.black;
import static javax.swing.JOptionPane.YES_OPTION;

public class WordBuilder extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int ROWS = 8, COLS = 12, MAX = 15;
    private static final String DOCUMENTS = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+ "\\GameHub\\";
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
    private final ArrayList<Integer> records = new ArrayList<>();
    private final ArrayList<String> username = new ArrayList<>();
    private final ArrayList<String> date = new ArrayList<>();

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

        try {
            BufferedReader in = new BufferedReader(new FileReader(DOCUMENTS + FILENAME));
            String s = in.readLine();
            while (s!=null){
                Integer newRecord = Integer.valueOf(s.substring(s.indexOf(":")+2, s.indexOf(";")));
                String userN = s.substring(0, s.indexOf(":"));
                String dt = s.substring(s.indexOf(";")+2);
                records.add(newRecord);
                username.add(userN);
                date.add(dt);
                s = in.readLine();
            }
        }catch (FileNotFoundException e){
            String message = "Data file was not found!";
            JOptionPane.showMessageDialog(this, message);
        } catch (IOException e) {
            String message = "Data cannot be read";
            JOptionPane.showMessageDialog(this, message);
        }
        if (score!=0) {
            sortArrays();
            if (records.size() < 10) {
                for (int i = records.size(); i < 10; i++) {
                    records.add(0, 0);
                    username.add(0, "");
                    date.add(0, "");
                }
            }
            for (int i = records.size() - 1; i != -1; i--) {
                if ((score >= records.get(i))) {
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date dat = new Date();
                    records.add(i, score);
                    username.add(i, user.getName()+user.getTAG());
                    date.add(i, dateFormat.format(dat));
                    break;
                }
            }
            for (int i = records.size()-1; i >= 0 ; i--) {
                if (records.get(i) == 0) {
                    records.remove(i);
                    username.remove(i);
                    date.remove(i);
                }
            }
            if (records.size()>10){
                records.remove(0);
                username.remove(0);
                date.remove(0);
            }
            sortArrays();
            saveRecord();
        }
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play again?",  JOptionPane.YES_NO_OPTION);
        if (choice == YES_OPTION){
            newGame();
        }else{
            gameMenuStatic();
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
    private void sortArrays(){
        if (records.size() !=0) {
            for (int i = records.size() - 1; i != 0; i--) {
                if (!(records.get(i) >= records.get(i - 1))) {
                    Collections.swap(records, i, i - 1);
                    Collections.swap(username, i, i - 1);
                    Collections.swap(date, i, i - 1);
                }
            }
            for (int i = records.size() - 1; i != 0; i--) {
                if (!(records.get(i) >= records.get(i - 1))) {
                    sortArrays();
                    break;
                }
            }
        }
    }
    private void saveRecord(){

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(DOCUMENTS+FILENAME));
            for (int i = 0; i < records.size()-1; i++) {
                String s = username.get(i) + ": " + records.get(i) + "; " + date.get(i);
                out.write(s);
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
