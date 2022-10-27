package com.griffty.Launcher;

import griffty.TitleLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

import static com.griffty.Launcher.Settings.darkTheme;
import static java.awt.BorderLayout.CENTER;
public class profileChooser extends JFrame{
    private final Dimension windowSize;
    private Font PlayAsGuestFont;
    private final JPanel mainPanel = new JPanel();
    private static final JButton[] deleteButton = new JButton[3];
    private Icon deleteButtonIcon = new ImageIcon();
    public static User[] users = {null, null, null};
    public static User user = null;
    public static Profile[] profiles = {null, null, null};
    private static Profile clickedPanel;
    private static final File[] DATAFILE = new File[3];
    private static final String DOCUMENTS = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+ "\\GameHub\\";
    private final JFrame frame = this;


    private void updateInterfaceData() {
        int width = (int) windowSize.getWidth();
        int height = (int) windowSize.getHeight();
        int size = Math.min(width, height);
        PlayAsGuestFont = new Font("comic sans ms", Font.BOLD, size / 20);
        try {
            String DELETEBUTTONFILANAME = "/bin.png";
            InputStream deleteButtonInput = getClass().getResourceAsStream(DELETEBUTTONFILANAME);
            assert deleteButtonInput != null;
            deleteButtonIcon = new ImageIcon(ImageIO.read(deleteButtonInput));

        } catch (Exception e) {
            String message = "Icon image cannot be read";
            JOptionPane.showMessageDialog(this, message);
        }
        updateUserData();
    }
    static void updateUserData(){
        File folder = new File(DOCUMENTS);
        if (folder.mkdir()){
            File[] listOfFiles = folder.listFiles();
            try {
                assert listOfFiles != null;
                for (File account : listOfFiles) {
                    BufferedReader in = new BufferedReader(new FileReader(account));
                    String version = in.readLine();
                    System.out.println(version);
                    String s;
                    int index;
                    s = in.readLine();
                    index = Integer.parseInt(s.substring(s.indexOf(":")+2));

                    users[index] = new User(index);
                    s = in.readLine();
                    users[index].setName(s.substring(s.indexOf(":")+2));
                    s = in.readLine();
                    users[index].setTAG(s.substring(s.indexOf(":")+2));
                    s = in.readLine();
                    users[index].setPass(s.substring(s.indexOf(":")+2));
                    in.readLine();
                    s = in.readLine();
                    users[index].bakersDozenStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].bakersDozenStatistic.setWins(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].bakersDozenStatistic.setCardsMoved(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    s = in.readLine();
                    users[index].wordBuilderStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].wordBuilderStatistic.setLettersUsed(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    s = in.readLine();
                    users[index].mazeStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].mazeStatistic.setWins(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].mazeStatistic.setCellsPassed(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    s = in.readLine();
                    users[index].wizardYesOrNoStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    s = in.readLine();
                    users[index].slidingTitlesStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].slidingTitlesStatistic.setWins(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].slidingTitlesStatistic.setTimesClicked(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    s = in.readLine();
                    users[index].watchYourStepStatistic.setAttempts(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].watchYourStepStatistic.setWins(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    s = in.readLine();
                    users[index].watchYourStepStatistic.setTilesOpened(Integer.parseInt(s.substring(s.indexOf(":")+2)));
                    in.readLine();
                    in.close();

                    DATAFILE[index] = account;
                    System.out.println(DATAFILE[index].getName());

                }
            } catch (Exception e) {
                String message = "Data files cannot be read";
                JOptionPane.showMessageDialog(null, message);
            }
        }

    }

    profileChooser(Dimension windowSize){
        this.windowSize = windowSize;
        try {
            String className = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        }catch (Exception ignored){}
        updateInterfaceData();
        initGui();

        setTitle("Ultimate Game Hub");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGui(){
        mainPanel.setBackground(Color.gray);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(windowSize);
        add(mainPanel);
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centralPanel, CENTER);

        TitleLabel titleLabel = new TitleLabel("Choose your profile: ", this);
        centralPanel.add(titleLabel);
        centralPanel.add(Box.createVerticalGlue());
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.X_AXIS));
        centralPanel.add(profilePanel);
        profilePanel.add(Box.createHorizontalGlue());
        for (int i = 0; i<3; i++){
            profiles[i] = new Profile(windowSize, i);
            if (users[i]!=null){
                profiles[i].setUser(users[i]);
                System.out.println("Users uploaded");
            }
            profiles[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    clickedPanel = (Profile) e.getSource();

                    if (clickedPanel.hasUser()){
                        login();
                    }else{
                        users[clickedPanel.getIndex()] = new User(windowSize, clickedPanel.getIndex(), frame);
                    }
                }
            });

            profilePanel.add(profiles[i]);
            profilePanel.add(Box.createHorizontalGlue());
        }


        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.X_AXIS));
        for (int i =0; i<3; i++) {
            deleteButton[i] = new JButton(deleteButtonIcon);
            deleteButton[i].setEnabled(false);

            deleteButton[i].addActionListener(e -> deleteUser((JButton) e.getSource()));

        }
        updateDeleteButtons();
        deletePanel.add(deleteButton[0]);
        deletePanel.add(Box.createRigidArea(new Dimension(240, 0)));
        deletePanel.add(deleteButton[1]);
        deletePanel.add(Box.createRigidArea(new Dimension(240, 0)));
        deletePanel.add(deleteButton[2]);
        centralPanel.add(deletePanel);
        centralPanel.add(Box.createVerticalGlue());

        JButton playAsGuest = new JButton("Play as guest");
        playAsGuest.setFont(PlayAsGuestFont);
        playAsGuest.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAsGuest.addActionListener(e -> gameMenu());
        centralPanel.add(playAsGuest);
        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        if (darkTheme){
            mainPanel.setBackground(Color.black);
            centralPanel.setBackground(Color.black);
            profilePanel.setBackground(Color.black);
            deletePanel.setBackground(Color.black);
            titleLabel.setBackground(Color.black);
            titleLabel.setForeground(Color.lightGray);
        }
    }

    public static void saveData(){
            for (User user : users) {
                if (user != null) {
                    File datafile = new File(DOCUMENTS + user.getName() + ".txt");
                    int find = 0;
                    for (int i = 0; i < 3 && find == 0; i++) {
                        if (DATAFILE[i] != null) {
                            if ((user.getName() + ".txt").equals(DATAFILE[i].getName())) {
                                datafile = DATAFILE[i];
                                find = 1;
                            } else {
                                System.out.println("new file " + datafile.getPath() + " was created");
                            }
                        } else {
                            System.out.println("file wasn't found");
                        }
                    }
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(datafile));
                        out.write("0.0v");
                        out.newLine();
                        out.write("index: " + user.getIndex());
                        out.newLine();
                        out.write("name: " + user.getName());
                        out.newLine();
                        out.write("TAG: " + user.getTAG());
                        out.newLine();
                        out.write("pass: " + user.getPass());
                        out.newLine();
                        out.write("//Baker's Dozen statistic");
                        out.newLine();
                        out.write("Total attempts: " + user.bakersDozenStatistic.getAttempts());
                        out.newLine();
                        out.write("Total wins: " + user.bakersDozenStatistic.getWins());
                        out.newLine();
                        out.write("Total cards moved: " + user.bakersDozenStatistic.getCardsMoved());
                        out.newLine();
                        out.write("//Word Builder statistic");
                        out.newLine();
                        out.write("Total uses: " + user.wordBuilderStatistic.getAttempts());
                        out.newLine();
                        out.write("Total letters used: " + user.wordBuilderStatistic.getLettersUsed());
                        out.newLine();
                        out.write("//Maze statistic: ");
                        out.newLine();
                        out.write("Total attempts: " + user.mazeStatistic.getAttempts());
                        out.newLine();
                        out.write("Total wins: " + user.mazeStatistic.getWins());
                        out.newLine();
                        out.write("Total cells traveled: " + user.mazeStatistic.getCellsPassed());
                        out.newLine();
                        out.write("//Wizard Yes Or No statistic");
                        out.newLine();
                        out.write("Total uses: " + user.wizardYesOrNoStatistic.getAttempts());
                        out.newLine();
                        out.write("//Sliding Tiles");
                        out.newLine();
                        out.write("Total attempts: " + user.slidingTitlesStatistic.getAttempts());
                        out.newLine();
                        out.write("Total wins: " + user.slidingTitlesStatistic.getWins());
                        out.newLine();
                        out.write("Total times clicked: " + user.slidingTitlesStatistic.getTimesClicked());
                        out.newLine();
                        out.write("//Watch Your Step");
                        out.newLine();
                        out.write("Total attempts: " + user.watchYourStepStatistic.getAttempts());
                        out.newLine();
                        out.write("Total wins: " + user.watchYourStepStatistic.getWins());
                        out.newLine();
                        out.write("Total tiles opened: " + user.watchYourStepStatistic.getTilesOpened());
                        out.close();
                        System.out.println("Data saved");
                    } catch (IOException e) {
                        String message = "Users data cannot be saved";
                        JOptionPane.showMessageDialog(null, message);
                    }
                    DATAFILE[user.getIndex()] = datafile;
                }
            }
        }
    private void deleteUser(JButton clickedButton){
        for (int i = 0; i < 3; i++){
            if (clickedButton == deleteButton[i]){
                if (DATAFILE[i].delete()){
                    profiles[i].setUser(null);
                    users[i] = null;
                    frame.repaint();
                    deleteButton[i].setEnabled(false);
                }else {
                    String message = "User cannot be deleted";
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        }
    }
    private static void updateDeleteButtons(){
        for (int i = 0; i<3; i++){
            if (users[i]!=null){
            deleteButton[i].setEnabled(true);
            }
        }
    }
    public static void repaintPanel(){
        clickedPanel.setUser(users[clickedPanel.getIndex()]);
        deleteButton[clickedPanel.getIndex()].setEnabled(true);
        if (users[clickedPanel.getIndex()].getTAG().equals("-1")) {
            users[clickedPanel.getIndex()] = null;
        }else {
            profileChooser.saveData();
            profileChooser.updateUserData();
        }
        clickedPanel = null;
        updateDeleteButtons();

    }
    private void login(){
        Login login = new Login(windowSize, clickedPanel.getUser(), this);
    }
    private void gameMenu(){
        new GameMenu();
        dispose();
    }
    public static void gameMenuStatic(){
        new GameMenu();
    }
    public static void newLauncherStatic(){
        new Launcher();
    }

}
