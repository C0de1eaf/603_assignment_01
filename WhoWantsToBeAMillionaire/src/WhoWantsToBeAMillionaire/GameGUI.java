package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI extends JPanel {

    int width = 1000;
    int height = 1000;

    private JTextField textField;
    private JButton button;

    public GameGUI() {
        setLayout(new BorderLayout());

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
        textField = new JTextField(10);
        inputPanel.add(textField);

        // Create button
        button = new JButton("Submit");
        button.setEnabled(false); // Disable the button initially

        // Add button to the input panel
        inputPanel.add(button);

        add(inputPanel, BorderLayout.CENTER);
        textField.setToolTipText("<html><b><font color=red>"
                + "Please enter some text here" + "</font></b></html>");

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textField.getText();
                System.out.println("User input: " + userInput);
                textField.setText("");  // Clear the text field
            }
        });

        // Add key event listener
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                String content = textField.getText();
                if (!content.equals("")) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }
}
