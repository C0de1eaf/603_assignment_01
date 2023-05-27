package WhoWantsToBeAMillionaire;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends JFrame {

    private int widthWindow = 1000;
    private int heightWindow = 1000;
    private String userName;
    private String fullName;

    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JButton nameSubmitButton;

    public GameGUI() {
        setTitle("Who Wants To Be A Millionaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(widthWindow, heightWindow);

        createGUI();

        setVisible(true); // Show the GUI
    }

    private void createGUI() {
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
        firstNameInput = createTextField();
        inputPanel.add(firstNameInput);

        // Add input text field to the input panel
        lastNameInput = createTextField();
        inputPanel.add(lastNameInput);

        // Create button
        nameSubmitButton = new JButton("Submit");
        nameSubmitButton.setPreferredSize(new Dimension(100, 30)); // Set the preferred size
        nameSubmitButton.setEnabled(false);

        // Add button to the input panel
        inputPanel.add(nameSubmitButton);

        add(inputPanel, BorderLayout.CENTER);

        firstNameInput.setToolTipText("<html><b><font color=grey>"
                + "Please enter some text here" + "</font></b></html>");

        firstNameInput.addActionListener(e -> {
            userName = firstNameInput.getText();
            if (!userName.isEmpty()) {
                System.out.println("User input: " + userName);
            }
            firstNameInput.setText("");  // Clear the text field
            updateSubmitButtonState();
        });

        nameSubmitButton.addActionListener(e -> {
            if (fullName != null) {
                System.out.println("User input: " + fullName);
            }
            firstNameInput.setText("");  // Clear the text field
            lastNameInput.setText("");  // Clear the text field
            updateSubmitButtonState();
        });

        // Set document filters for the text fields
        ((AbstractDocument) firstNameInput.getDocument()).setDocumentFilter(createDocumentFilter());
        ((AbstractDocument) lastNameInput.getDocument()).setDocumentFilter(createDocumentFilter());
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20); // Adjust the length as desired
        textField.setPreferredSize(new Dimension(100, 30)); // Set the preferred size
        return textField;
    }

    private DocumentFilter createDocumentFilter() {
        return new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (Character.isLetter(c) || c == '-' || c == '\'') {
                        sb.append(c);
                    }
                }
                super.replace(fb, offset, length, sb.toString(), attrs);
                updateSubmitButtonState();
            }
        };
    }

    private void updateSubmitButtonState() {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            nameSubmitButton.setEnabled(true);
            fullName = firstName + " " + lastName;
        } else {
            nameSubmitButton.setEnabled(false);
            fullName = null;
        }
    }
}
