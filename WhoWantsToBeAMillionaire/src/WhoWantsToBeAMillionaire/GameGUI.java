package WhoWantsToBeAMillionaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JPanel {

    int width = 1000;
    int height = 1000;

    private JTextField textField;

        public GameGUI() {
        setLayout(new BorderLayout());

        // Create panel for title label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Add title label to the title panel
        JLabel titleLabel = new JLabel("Who Wants To Be A Millionaire");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Create panel for input text box
        JPanel inputPanel = new JPanel(new GridBagLayout());
        
        // Center-align the input text box both vertically and horizontally
        inputPanel.setPreferredSize(new Dimension(200, 200));
        
        // Add input text box to the input panel
        textField = new JTextField(10);
        inputPanel.add(textField);
        
        add(inputPanel, BorderLayout.CENTER);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textField.getText();
                System.out.println("User input: " + userInput);
                textField.setText("");  // Clear the text field
            }
        });
    }
}
