package TP2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TitleScreen extends JFrame {
    public TitleScreen() {
        super("Title Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Title label
        JButton titleLabel = new JButton("Whatever 2D");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setEnabled(false);
        titleLabel.setBorderPainted(false);
        titleLabel.setContentAreaFilled(false);
        panel.add(titleLabel, constraints);

        constraints.gridy++;

        // Start Game button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the title screen
                MainInterface mainInterface = new MainInterface();
                mainInterface.setVisible(true); // Show the main game interface
            }
        });
        panel.add(startButton, constraints);

        constraints.gridy++;

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the program
            }
        });
        panel.add(quitButton, constraints);

        add(panel);
    }
}
