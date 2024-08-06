import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Menu extends JFrame {
    
    public static int Selectedlevel = 1;

    public Menu() {
        
        setTitle(" Menu Window ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(711, 400);
        setResizable(false);
        
        int screenX = 711;
        int buttonX = 150;
        int buttonY = 40;
        
        ImageIcon background = new ImageIcon("background.jpg");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 711, 400);
        add(backgroundLabel);
        JLayeredPane layeredPane = getLayeredPane();
        
        JLabel memoryCardLabel = new JLabel("MEMORY CARD GAME");
        memoryCardLabel.setFont(new Font("Italic", Font.BOLD, 35));
        memoryCardLabel.setForeground(new Color(23, 216, 230)); 
        memoryCardLabel.setBounds((screenX/2) - 190, 50, 400, 100);
        layeredPane.add(memoryCardLabel, JLayeredPane.PALETTE_LAYER);
       
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds((screenX/2)-(buttonX/2), 155, buttonX, buttonY);
        layeredPane.add(startGameButton, JLayeredPane.PALETTE_LAYER);
        
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GamePanel gamePanel = new GamePanel();
                dispose();
            }
        });
        
        JButton selectLevel = new JButton(" Select Level ");
        selectLevel.setBounds((screenX/2)-(buttonX/2), 205, buttonX, buttonY);
        layeredPane.add(selectLevel, JLayeredPane.PALETTE_LAYER); 
        selectLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog levelBOX = new JDialog(Menu.this, "Select Level", true);
                levelBOX.setLayout(new GridLayout(3, 1)); 
                
                JButton level1 = new JButton("LEVEL 1");
                JButton level2 = new JButton("LEVEL 2");
                JButton level3 = new JButton("LEVEL 3");
                
     
                
                levelBOX.add(level1);
                levelBOX.add(level2);
                levelBOX.add(level3);
                
                level1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        JOptionPane.showMessageDialog(Menu.this, "LEVEL 1 selected!");
                        Selectedlevel = 1;
                        
                        levelBOX.dispose(); 
                    }
                });
                
                level2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        JOptionPane.showMessageDialog(Menu.this, "LEVEL 2 selected!");
                        Selectedlevel = 2;
                        
                        levelBOX.dispose(); 
                    }
                });
                
                level3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        JOptionPane.showMessageDialog(Menu.this, "LEVEL 3 selected!");
                        Selectedlevel = 3;
                        
                        levelBOX.dispose();
                    }
                });
                
                levelBOX.setSize(200, 160);
                levelBOX.setLocationRelativeTo(Menu.this);
                
                levelBOX.setVisible(true);
            }
        });

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setBounds((screenX / 2) - (buttonX / 2), 255, buttonX, buttonY);
        instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane pane = new JOptionPane(" Instructions:\nThere are 3 levels in the game. It gets gradually harder! \nMatch all pairs of cards to win!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
                JDialog dialog = pane.createDialog(Menu.this, "Instructions");
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false); 
                    }
                });
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(okButton);
                pane.add(buttonPanel, BorderLayout.SOUTH); 
                dialog.setSize(500, 175); 
                dialog.setLocationRelativeTo(Menu.this); 
                dialog.setVisible(true); 
            }
        });
        layeredPane.add(instructionsButton, JLayeredPane.PALETTE_LAYER);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds((screenX/2)-(buttonX/2), 305, buttonX, buttonY);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);
        
        setVisible(true);
    }
    public static void main(String[] args) {
        new Menu();
    }
}
