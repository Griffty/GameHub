package griffty;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;

import static com.griffty.Launcher.Launcher.newProfileChooser;
import static com.griffty.Launcher.Settings.saveSettings;
import static com.griffty.Launcher.profileChooser.*;
import static java.awt.Color.black;

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
        JPanel topPanel = new JPanel();
         topPanel.setLayout(new BorderLayout());
         add(topPanel);

         switch (title){
             case "Statistic", "Choose what do you want to play:" -> font = new Font("Comic Sans", Font.BOLD,24);
             case "Choose your profile: " -> {
                 setMaximumSize(new Dimension(10000, 100));
                 font = new Font("Comic Sans", Font.BOLD,24);
             }
         }
         horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
         topPanel.add(horizontalPanel);
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

         JButton backButton = new JButton(new ImageIcon(backImage));
         backButton.setFocusable(false);

         backButton.addActionListener(e -> {
             saveData();
             frame.dispose();
            switch (title){
                case "Choose what do you want to play:" -> newProfileChooser();
                case "Choose your profile: " -> newLauncherStatic();
                case "Settings" -> saveSettings();
                default -> gameMenuStatic();
            }



         });
         JButton hintButton = new JButton(new ImageIcon(hintImage));
         hintButton.setFocusable(false);
         horizontalPanel.add(backButton);
         horizontalPanel.add(Titlelabel);
         horizontalPanel.add(hintButton);
        }
        public void setText(String text){
            Titlelabel.setText(text);
        }

}
