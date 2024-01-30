package TP2D;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverMenu extends JDialog {
    public GameOverMenu(boolean win) {
        setTitle("Game Over");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(3, 1)); // Change layout to vertical

        JLabel label;
        if ( win ) {
            label = new JLabel("You win!");
        } else {
            label = new JLabel("You lose!");            
        }
        panel.add(label);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });
        panel.add(restartButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Terminate the program
            }
        });
        panel.add(quitButton);

        add(panel);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}