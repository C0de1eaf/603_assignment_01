package WhoWantsToBeAMillionaire;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends JFrame {

    int widthWindow = 1000;
    int heightWindow = 1000;
    public String userName;
    public String firstName;
    public String lastName;
    public String fullName;

    private JTextField firstNameInput;
    private JTextField lastNameInput;
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
        firstNameInput = new JTextField(20); // Adjust the length as desired
        firstNameInput.setPreferredSize(new Dimension(100, 30)); // Set the preferred size
        inputPanel.add(firstNameInput);

        // Add input text field to the input panel
        lastNameInput = new JTextField(20); // Adjust the length as desired
        lastNameInput.setPreferredSize(new Dimension(100, 30)); // Set the preferred size
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

        firstNameInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userName = firstNameInput.getText();
                if (!userName.isEmpty()) {
                    System.out.println("User input: " + userName);
                }
                firstNameInput.setText("");  // Clear the text field
                updateSubmitButtonState();
            }
        });

        nameSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fullName != null) {
                    System.out.println("User input: " + fullName);
                }
                firstNameInput.setText("");  // Clear the text field
                lastNameInput.setText("");  // Clear the text field
                updateSubmitButtonState();
            }
        });

        // Add key event listener
        firstNameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent event) {
                String firstNameContent = firstNameInput.getText();
                String lastNameContent = lastNameInput.getText();
                if (!firstNameContent.isEmpty() && !lastNameContent.isEmpty()) {
                    nameSubmitButton.setEnabled(true);
                } else {
                    nameSubmitButton.setEnabled(false);
                }
            }
        });

        // Limit the maximum number of characters for the text fields
        ((AbstractDocument) firstNameInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                int maxLength = 18;
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - maxLength;
                if (overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }
                super.replace(fb, offset, length, text, attrs);
                updateSubmitButtonState();
            }
        });

        ((AbstractDocument) lastNameInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                int maxLength = 18;
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - maxLength;
                if (overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }
                super.replace(fb, offset, length, text, attrs);
                updateSubmitButtonState();
            }
        });

        setVisible(true); // Show the GUI
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
