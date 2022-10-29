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
        Titlelabel = new JLabel(title);
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
         Titlelabel.setText(title);
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
                case "Choose what do you want to play:" -> new profileChooser(windowSize);
                case "Choose your profile: " -> newLauncherStatic();
                default -> gameMenuStatic();
            }
         });
        JColoredButton hintButton = new JColoredButton("");
        hintButton.setIcon(new ImageIcon(hintImage));
        hintButton.setFocusable(false);
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
