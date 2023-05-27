import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {

    private JButton playButton;
    private JButton leaderboardButton;
    private JButton exitButton;

    private int buttonWidth = 150;
    public int buttonHeight = 50;

    public MenuGUI() {
        setTitle("Who Wants To Be A Millionaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null); // Center the window on the screen

        // Create a panel to hold the buttons with vertical layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Create the buttons
        playButton = createButton("Play");
        leaderboardButton = createButton("Leaderboard");
        exitButton = createButton("Exit");

        exitButton.addActionListener(e -> System.exit(0));

        // Set the size and alignment for the buttons
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        playButton.setMaximumSize(buttonSize);
        leaderboardButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add vertical spacing between buttons
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adjust the spacing as needed
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adjust the spacing as needed
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Set the layout manager for the content pane
        getContentPane().setLayout(new BorderLayout());

        // Add the button panel to the content pane
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Show the GUI
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false); // Remove button focus border
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuGUI::new);
    }
}
