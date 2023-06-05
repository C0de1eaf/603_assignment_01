package WhoWantsToBeAMillionaire;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GameGUI extends JPanel {

    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JButton nameSubmitButton;
    private String fullName;
    private JButton returnButton;
    private JLabel instructionsLabel;

    public String getUserName() {
        return fullName;
    }

    public void setUserName(String fullName) {
        this.fullName = fullName;
    }

    public GameGUI(CardLayout cardLayout, JPanel cards) {
        createGUI(cardLayout, cards);
    }

    public void createGUI(CardLayout cardLayout, JPanel cards) {

        // Create panel for input and button
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
//        createReturnButton(cardLayout, cards);

        instructionsLabel = createInstructionsLabel();
        add(instructionsLabel);

        // Add input text field to the input panel
        firstNameInput = createFirstNameInput();
        inputPanel.add(firstNameInput);

        // Add input text field to the input panel
        lastNameInput = createLastNameInput();
        inputPanel.add(lastNameInput);

        // Create button
        nameSubmitButton = createNameSubmitButton();

        // Add button to the input panel
        inputPanel.add(nameSubmitButton);

        add(inputPanel, BorderLayout.SOUTH);

        firstNameInput.addActionListener(e -> {
            fullName = firstNameInput.getText();
            if (!fullName.isEmpty()) {
                System.out.println("User input: " + fullName);
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

            // Hide inputPanel and nameSubmitButton, and show messageLabel
            inputPanel.setVisible(false);
            nameSubmitButton.setVisible(false);
            instructionsLabel.setVisible(true);
        });

        // Set document filters for the text fields
        ((AbstractDocument) firstNameInput.getDocument()).setDocumentFilter(createDocumentFilter());
        ((AbstractDocument) lastNameInput.getDocument()).setDocumentFilter(createDocumentFilter());
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
                String newText = sb.toString();
                int newLength = fb.getDocument().getLength() + newText.length() - length;
                if (newLength <= 18) {
                    super.replace(fb, offset, length, newText, attrs);
                }
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
//            fullName = null;
        }
    }

    public JPanel createReturnButton(CardLayout cardLayout, JPanel cards) {
        /*
         * RETURN BUTTON CONTENT
         */

        // Create returnButton panel
        JPanel returnButtonPanel = new JPanel();
        returnButtonPanel.setLayout(new BoxLayout(returnButtonPanel, BoxLayout.Y_AXIS));

        // Create the return button
        try {
            BufferedImage panelImage = ImageIO.read(new File("resources/cropped_return.png"));
            ImageIcon returnButtonIcon = new ImageIcon(panelImage);

            returnButton = new JButton(returnButtonIcon);

            // Add spacing around the buttons border
            returnButton.setMargin(new Insets(0, 5, 0, 5));

            // Add text to the right of the image
            returnButton.setText("Return");

            Font returnButtonFont = new Font("Arial", Font.BOLD, 26);
            returnButton.setFont(returnButtonFont);

            // Add space between the image and the text
            returnButton.setIconTextGap(10);

            // Add ActionListener to return button
            returnButton.addActionListener(e -> {
                cardLayout.show(cards, "menuGUI");
            });

            // Background default colour
            returnButton.setBackground(new Color(255, 215, 0));

            // Add interactivity to return button
            returnButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    returnButton.setBackground(new Color(255, 165, 0));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    returnButton.setBackground(new Color(255, 215, 0));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    returnButton.setBackground(new Color(255, 165, 0));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    returnButton.setBackground(new Color(255, 215, 0));
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // Add the components to the return button panel
        returnButtonPanel.add(returnButton);

        // Set up the GridBagConstraints for the return button
        GridBagConstraints gbcReturnButtonPanel = new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 20, 20, 0), 0, 0);

        // Add returnButtonPanel to the main panel
        add(returnButtonPanel, gbcReturnButtonPanel);
        return returnButtonPanel;
    }

    private JTextField createFirstNameInput() {
        JTextField firstNameTextField = new JTextField(20);
        firstNameTextField.setToolTipText("<html><b><font color=grey>"
                + "Enter your first name here" + "</font></b></html>");
        firstNameTextField.setPreferredSize(new Dimension(0, 37));
        return firstNameTextField;
    }

    private JTextField createLastNameInput() {
        JTextField lastNameTextField = new JTextField(20);
        lastNameTextField.setToolTipText("<html><b><font color=grey>"
                + "Enter your last name here" + "</font></b></html>");
        lastNameTextField.setPreferredSize(new Dimension(0, 37));
        return lastNameTextField;
    }

    private JButton createNameSubmitButton() {
        JButton nameSubmitButtonCreate = new JButton("Submit");
        Font submitTextFont = new Font("Arial", Font.BOLD, 10);
        nameSubmitButtonCreate.setPreferredSize(new Dimension(100, 37));
        nameSubmitButtonCreate.setEnabled(false);
        nameSubmitButtonCreate.setFont(submitTextFont);
        return nameSubmitButtonCreate;
    }

    private JLabel createInstructionsLabel() {
        JLabel instructionsLabelCreate = new JLabel("<html>Welcome to the Who Wants To Be A Millionaire game.<br/>"
                + "You will be asked a total of 10 questions with varying <br/>"
                + "difficulty as the questions go on. Please answer them by<br/>"
                + "clicking on the correct answer in order to move on.</html>");
        Font font = new Font("Arial", Font.PLAIN, 20);
        instructionsLabelCreate.setFont(font);
        instructionsLabelCreate.setVisible(false);
        return instructionsLabelCreate;
    }

}
