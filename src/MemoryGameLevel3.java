
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;


public class MemoryGameLevel3 extends JFrame {
    private int previousCardIndex = -1;
    private int matchesFound = 0;
    private JButton previousButton = null;
    public static int triesLeft = 12;
    public String nameString;
    private boolean timerRunning = false;
    private ArrayList<Integer> correctMatches = new ArrayList<>();
    JLabel triesLeftLabel = new JLabel("    " + " Tries Left : " + triesLeft);

    public MemoryGameLevel3() {
    	
    	Menu.Selectedlevel = 3;
    	
        setTitle("Flash Card Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        gameMenu.add(restartItem);
        gameMenu.add(highScoresItem);
        menuBar.add(Box.createHorizontalStrut(170));
        
        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutDeveloperItem = new JMenuItem("About the Developer");
        aboutDeveloperItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showAboutDev();
            }
        });
        
        JMenuItem aboutGameItem = new JMenuItem("About the Game");
        aboutGameItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showInfo();
            }
        });
        
        aboutMenu.add(aboutDeveloperItem);
        aboutMenu.add(aboutGameItem);
        
        JMenu exitMenu = new JMenu("Exit");
        JMenuItem returnToMenu = new JMenuItem("Return to Menu");
        exitMenu.add(returnToMenu);
        
        menuBar.add(gameMenu);
        menuBar.add(aboutMenu);
        menuBar.add(exitMenu);

        setJMenuBar(menuBar);


        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Color customColor = new Color(178, 33, 33);
        levelPanel.setBackground(customColor);
        JLabel levelLabel = new JLabel(" LEVEL " + Menu.Selectedlevel + " ");
        levelLabel.setFont(new Font("Arial", Font.BOLD, 28));
        triesLeftLabel.setFont(new Font("Arial", Font.BOLD, 28));
        levelLabel.setForeground(Color.WHITE);
        triesLeftLabel.setForeground(Color.WHITE);
        
        levelPanel.add(levelLabel);
        levelPanel.add(triesLeftLabel);
        add(levelPanel, BorderLayout.NORTH);


        JPanel gamePanel = new JPanel(new GridLayout(4, 4));
        createCardDistribution(gamePanel);
        add(gamePanel, BorderLayout.CENTER);

        restartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                triesLeft = 12;
                GamePanel.score = 0;
                new MemoryGameLevel3();
            }
        });

        highScoresItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHighScores();
            }
        });

        returnToMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                triesLeft = 12;
                GamePanel.score = 0;
                Menu menu = new Menu();
            }
        });


        setVisible(true);
    }

    private void createCardDistribution(JPanel gamePanel) {
        ArrayList<Integer> cardIndexes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cardIndexes.add(i);
        }
        Collections.shuffle(cardIndexes);

        ArrayList<Integer> selectedIndexes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int randomIndex = cardIndexes.get(i);
            selectedIndexes.add(randomIndex);
            selectedIndexes.add(randomIndex);

            Collections.shuffle(selectedIndexes);
        }

        for (int i = 0; i < 16; i++) {
            int cardIndex = selectedIndexes.get(i);
            JButton cardButton = new JButton(new ImageIcon("Level3-GamingComputerAssets/no_image.png"));
            cardButton.putClientProperty("cardIndex", cardIndex);
            cardButton.addActionListener(new CardButtonListener(cardIndex));
            gamePanel.add(cardButton);
        }
    }

    private class CardButtonListener implements ActionListener {
        private int cardIndex;

        public CardButtonListener(int cardIndex) {
            this.cardIndex = cardIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (timerRunning || correctMatches.contains(cardIndex)) {
                return;
            }

            button.setIcon(new ImageIcon("Level3-GamingComputerAssets/" + cardIndex + ".png"));

            if (previousCardIndex != -1  && previousButton != button) {
                if (isCorrectMatch(cardIndex, previousCardIndex)) {
                    matchesFound++;
                    GamePanel.score += 3;
                    System.out.println(GamePanel.score);
                    if (matchesFound == 8) {
                        updateScoreLabel();                     
                        showCongratsMessage();
                    }

                    button.setEnabled(false);
                    previousButton.setEnabled(false);
                    correctMatches.add(cardIndex);
                    correctMatches.add(previousCardIndex);
                } else {
                	GamePanel.score -= 3;
                	System.out.println(GamePanel.score);
                    triesLeft--;
                    updateScoreLabel();
                    triesLeftLabel.setText("     Tries Left : " + triesLeft);
                    Timer timer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            button.setIcon(new ImageIcon("Level3-GamingComputerAssets/no_image.png"));
                            previousButton.setIcon(new ImageIcon("Level3-GamingComputerAssets/no_image.png"));
                            resetCards();
                            timerRunning = false;
                        }
                    });
                    timer.setRepeats(false);
                    timerRunning = true;
                    timer.start();
                    if (triesLeft == 0) {
                    	GamePanel.score = 0;
                        showLostMessage();
                    }
                }
                previousCardIndex = -1;
            } else {
                previousCardIndex = cardIndex;
                previousButton = button;
            }
        }

    }

    private boolean isCorrectMatch(int firstCardIndex, int secondCardIndex) {
        return firstCardIndex == secondCardIndex;
    }

    private void updateScoreLabel() {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component innerComponent : panel.getComponents()) {
                    if (innerComponent instanceof JLabel) {
                        JLabel label = (JLabel) innerComponent;
                        if (label.getText().startsWith("Score:")) {
                            label.setText("Score: " + GamePanel.score);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("high_scores.csv", true))) {
            writer.write(playerName + "," + score + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showHighScores() {
        File highScoresFile = new File("high_scores.csv");

        if (!highScoresFile.exists()) {
            JOptionPane.showMessageDialog(this, "No high scores found!", "High Scores", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(highScoresFile))) {
            ArrayList<String> highScores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(line);
            }
            
            
            Collections.sort(highScores, new Comparator<String>() {
                @Override
                public int compare(String score1, String score2) {
                    int s1 = Integer.parseInt(score1.split(",")[1]);
                    int s2 = Integer.parseInt(score2.split(",")[1]);
                    return s2 - s1;
                }
            });

            StringBuilder highScoresText = new StringBuilder();
            int count = Math.min(highScores.size(), 10); 
            for (int i = 0; i < count; i++) {
                String[] parts = highScores.get(i).split(",");
                highScoresText.append(parts[0]).append(": ").append(parts[1]).append("\n");
            }
            JOptionPane.showMessageDialog(this, highScoresText.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    void showInfo() {
        JOptionPane.showMessageDialog(this, "This is a simple memory card game.\nMatch pairs of cards to win!", "About the Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAboutDev() {
        JOptionPane.showMessageDialog(this, "Developer: KAAN ODABAS \nStudent Number: 20210702046 ", "About the Developer", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCongratsMessage() {
        String playerName = JOptionPane.showInputDialog(this, "Congratulations, you won! Enter your name:");
        if (playerName != null && !playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Congrats " + playerName + ", you won!!\nScore: " + GamePanel.score, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            saveScore(playerName, GamePanel.score);
        } else {
            JOptionPane.showMessageDialog(this, "You won but your name was not provided. Score not saved.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showLostMessage() {
        JOptionPane.showMessageDialog(this, "You lost, try again!", "Try Again", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        Menu.Selectedlevel = Menu.Selectedlevel - 2;
        MemoryGameLevel1.triesLeft = 18;
        new MemoryGameLevel1();
    }


    private void resetCards() {
        JPanel gamePanel = (JPanel) getContentPane().getComponent(1);
        ArrayList<JButton> closedCards = new ArrayList<>();
        ArrayList<JButton> openCards = new ArrayList<>();

        for (Component component : gamePanel.getComponents()) {
            if (component instanceof JButton) {
                JButton cardButton = (JButton) component;
                int cardIndex = (int) cardButton.getClientProperty("cardIndex");
                if (cardButton.isEnabled() && !correctMatches.contains(cardIndex)) {
                    closedCards.add(cardButton);
                } else {
                    openCards.add(cardButton);
                }
            }
        }

        
        int n = closedCards.size();
        for (int i = n - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            JButton temp = closedCards.get(i);
            closedCards.set(i, closedCards.get(j));
            closedCards.set(j, temp);
        }

        
        Thread shuffleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < closedCards.size(); i++) {
                    JButton card = closedCards.get(i);
                    Point from = card.getLocation();
                    Point to = new Point((i % 4) * (gamePanel.getWidth() / 4), (i / 4) * (gamePanel.getHeight() / 4));
                    animateCardMovement(card, from, to);
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.removeAll();
                        for (JButton card : closedCards) {
                            gamePanel.add(card);
                        }
                        for (JButton card : openCards) {
                            gamePanel.add(card);
                        }

                        gamePanel.revalidate();
                        gamePanel.repaint();
                    }
                });
            }
        });

        shuffleThread.start();

    }


    private void animateCardMovement(JButton card, Point from, Point to) {
        Timer timer = new Timer(10, null);
        int duration = 350;
        int steps = duration / timer.getDelay();
        int dx = (to.x - from.x) / steps;
        int dy = (to.y - from.y) / steps;
        final int[] stepCount = {0}; 

        timer.addActionListener(e -> {
            int x = card.getLocation().x + dx;
            int y = card.getLocation().y + dy;
            card.setLocation(x, y);
            stepCount[0]++;
            if (stepCount[0] >= steps) {
                card.setLocation(to);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    
    

    public static void main(String[] args) {
        new MemoryGameLevel3();
    }
}
