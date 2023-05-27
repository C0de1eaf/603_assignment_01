package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {

    int widthWindow = 1000;
    int heightWindow = 1000;
    public String userName;

    private JTextField nameInput;
    private JButton nameSubmitButton;

    public GameGUI() {
        setTitle("Who Wants To Be A Millionaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(widthWindow, heightWindow);

        // Create panel for title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add title label to the title panel
        JLabel titleLabel = new JLabel("Who Wants To Be A Millionaire");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Create panel for input and button
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add input text field to the input panel
        nameInput = new JTextField(20); // Adjust the length as desired
        nameInput.setPreferredSize(new Dimension(200, 30)); // Set the preferred size
        inputPanel.add(nameInput);

        // Create button
        nameSubmitButton = new JButton("Submit");
        nameSubmitButton.setPreferredSize(new Dimension(100, 30)); // Set the preferred size
        nameSubmitButton.setEnabled(false);

        // Add button to the input panel
        inputPanel.add(nameSubmitButton);

        add(inputPanel, BorderLayout.CENTER);
        nameInput.setToolTipText("<html><b><font color=grey>"
                + "Please enter some text here" + "</font></b></html>");

        nameInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userName = nameInput.getText();
                if (!userName.isEmpty()) {
                    System.out.println("User input: " + userName);
                }
                nameInput.setText("");  // Clear the text field
            }
        });

        nameSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userName = nameInput.getText();
                if (!userName.isEmpty()) {
                    System.out.println("User input: " + userName);
                }
                nameInput.setText("");  // Clear the text field
            }
        });

        // Add key event listener
        nameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                String content = nameInput.getText();
                if (!content.isEmpty()) {
                    nameSubmitButton.setEnabled(true);
                } else {
                    nameSubmitButton.setEnabled(false);
                }
            }
        });

        setVisible(true); // Show the GUI
    }

}
