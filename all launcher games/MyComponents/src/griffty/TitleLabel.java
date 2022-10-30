package griffty;

import com.griffty.Launcher.JColoredButton;
import com.griffty.Launcher.profileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;

import static com.griffty.Launcher.Launcher.DarkTheme;
import static com.griffty.Launcher.Launcher.windowSize;
import static com.griffty.Launcher.profileChooser.*;
import static java.awt.Color.black;
import static java.awt.Color.darkGray;

public class TitleLabel extends JPanel {
        @Serial
        private static final long serialVersionUID = 1L;
        public JLabel Titlelabel;
        public JPanel horizontalPanel = new JPanel();
        private BufferedImage backImage;
        private BufferedImage hintImage;

    public TitleLabel(String title, JFrame frame) {

        String profileChooserHint = "Here are 3 empty profiles, you can create new one by clicking on big plus button.\nAfter you sign up, your profile will be saved and you can sign in anytime.\nOnce profile was created you can always delete it by clicking delete button under profile.\nAlso, if you don't want to use profile, you can play as a guest, but then statistic will be disabled!";
        String gameMenuHint = "Here you can choose what game you want to play, or if you have account check your statistic.";
        String BakersDozenHint = "Baker's Dozen is the game where you should make 4 card columns that starts with ace and end with king.\nBesides putting card only into 4 columns, you can also put cards one on top of the other if your card rank is lower.";
        String WordBuilderHint = "Word Builder is a game where you build a word from the letters in the top column.\nTotal score is the sum of all values multiplied by the number of letters.\nAlso, the program will only accept real English words";
        String MazeHint = "Maze is a game where you must reach exit (red point) using a,w,s,d buttons to move.\nAntimaze is the same, but you can move only through the walls.";
        String WizardYesOrNoHint = "This wizard will help you to choose. Just click on the ball.";
        String SlidingTilesHint = "In sliding tiles you must press on tiles near empty space to move them and create correct image.\nIf standard image become boring you can load your own by using file/open or change your grid size ";
        String WatchYourStepHint = "In watch your steps you must click the tiles to open them. There are safe and bomb tiles.\nClick on safe tile will open it and if this tile is near the bomb it will show numbers of bombs near it.\nIf you click on safe tile without number, if will open all nearest safe tiles.\nThe goal of this game is to open all safe tiles.";
        String StatisticHint = "This is your statistic menu. Here you can check some interesting date of each different game.";

        Titlelabel = new JLabel();
        Font font = new Font("Comic Sans", Font.BOLD,18);
         switch (title){
             case "Statistic", "Choose what do you want to play:" -> font = new Font("Comic Sans", Font.BOLD,24);
             case "Choose your profile: " -> {
                 setMaximumSize(new Dimension(10000, 100));
                 font = new Font("Comic Sans", Font.BOLD,24);
             }
         }
         horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
         add(horizontalPanel);
         Titlelabel.setFont(font);
         Titlelabel.setForeground(black);
         Titlelabel.setOpaque(true);
         Titlelabel.setAlignmentX(CENTER_ALIGNMENT);
         Titlelabel.setText("    " + title + "    ");
         try {
             InputStream inputbackimage = getClass().getResourceAsStream("/back-button.png");
             InputStream inputhintimage = getClass().getResourceAsStream("/hint-16.png");
             assert inputbackimage != null;
             assert inputhintimage != null;
             backImage = ImageIO.read(inputbackimage);
             hintImage = ImageIO.read(inputhintimage);

         }catch (Exception e){
            String message = "Error! Make sure all necessary files and images are present and try again";
             JOptionPane.showMessageDialog(this, message);
         }

         JColoredButton backButton = new JColoredButton("");
         backButton.setIcon(new ImageIcon(backImage));
         backButton.setFocusable(false);

         backButton.addActionListener(e -> {
             saveData();
             frame.dispose();
            switch (title){
                case "Choose your profile:" -> newLauncherStatic();
                case "Choose what do you want to play:" -> new profileChooser(windowSize);

                default -> gameMenuStatic();
            }
         });
        JColoredButton hintButton = new JColoredButton("");
        hintButton.setIcon(new ImageIcon(hintImage));
        hintButton.setFocusable(false);
        hintButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, switch (title){
            case "Choose your profile:" -> profileChooserHint;
            case "Choose what do you want to play:" -> gameMenuHint;
            case "Statistic" -> StatisticHint;
            case "Baker's Dozen" -> BakersDozenHint;
            case "Word Builder" -> WordBuilderHint;
            case "Maze" -> MazeHint;
            case "Wizard yes or no" -> WizardYesOrNoHint;
            case "Sliding Titles" -> SlidingTilesHint;
            case "Watch your step" -> WatchYourStepHint;
            default -> "";
        }));
        horizontalPanel.add(backButton);
        horizontalPanel.add(Titlelabel);
        horizontalPanel.add(hintButton);
        if (DarkTheme){
            horizontalPanel.setBackground(darkGray);
            Titlelabel.setBackground(darkGray);
            setBackground(darkGray);
        }
    }
        public void setText(String text){
            Titlelabel.setText(text);
        }
}
