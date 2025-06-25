import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Mario: Whac A Mole!");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9]; // 3x3 grid of buttons
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile; // Current mole tile
    JButton currPlantTile; // Current plant tile

    Random random = new Random();
    Timer setMoleTimer; // we need it to move at random in a certain time
    Timer setPlantTimer;
    int score; // score of the game

    WhacAMole() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3)); // 3x3 grid for the moles
        frame.add(boardPanel);

        // plantIcon = new ImageIcon(getClass().getResource("./piranha.png")); // Load
        // the Piranha image

        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score = 0; // Initialize score
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile; // Store the button in the board array
            boardPanel.add(tile); // Add the button to the board panel
            // tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();

                    if (tile == currMoleTile) {
                        score += 10; // Increase score by 10 if mole is hit
                        textLabel.setText("Score: " + Integer.toString(score));

                    } else if (tile == currPlantTile) {
                        textLabel.setText("Game Over: + " + Integer.toString(score));
                        setMoleTimer.stop(); // Stop the mole timer
                        setPlantTimer.stop(); // Stop the plant timer
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false); // Disable all tiles
                        }
                    }

                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null); // Remove the mole icon from the current tile
                    currMoleTile = null;
                }
                int num = random.nextInt(9); // Get a random index for the mole
                JButton tile = board[num]; // Get the button at that index

                // if tile is occupied by plant, then skip tile for this turn
                if (currPlantTile == tile)
                    return;

                // set title to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon); // Set the mole icon on the current tile

            }

        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null); // Remove the plant icon from the current tile
                    currPlantTile = null;
                }
                int num = random.nextInt(9); // Get a random index for the plant
                JButton tile = board[num]; // Get the button at that index

                if (currMoleTile == tile)
                    return;

                // set title to plant
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon); // Set the plant icon on the current tile

            }

        });

        setMoleTimer.start(); // Start the timer to set moles at intervals
        setPlantTimer.start(); // Start the timer to set plants at intervals
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WhacAMole(); // Create an instance of WhacAMole
    }
}
