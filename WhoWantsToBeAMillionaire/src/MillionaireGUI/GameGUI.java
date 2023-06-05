package MillionaireGUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameGUI extends JPanel {

    private JPanel returnButtonPanel;
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
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        // Create input panel with input fields and button
        JPanel inputPanel = createInputPanel();

        // Create buttons
        JButton continueButton = new JButton("Continue");
        JButton aButton = new JButton("Option A");
        JButton bButton = new JButton("Option B");
        JButton cButton = new JButton("Option C");
        JButton dButton = new JButton("Option D");
        returnButton = createReturnButton(cardLayout, cards);
        JButton fiftyFiftyButton = new JButton("50 - 50");
        JButton atAButton = new JButton("AtA");
        JButton pafButton = new JButton("PaF");
        JButton fillerButton = new JButton("Filler");

        // Add instructions label
        instructionsLabel = createInstructionsLabel();

        // Set GroupLayout's horizontal and vertical groups
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(2)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(instructionsLabel)
                        .addComponent(inputPanel)
//                        .addComponent(returnButtonPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(continueButton)
                                        .addComponent(returnButton))
                                .addGap(2, 2, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(aButton)
                                        .addComponent(fiftyFiftyButton))
                                .addGap(2, 2, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(bButton)
                                        .addComponent(atAButton))
                                .addGap(2, 2, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(cButton)
                                        .addComponent(pafButton))
                                .addGap(2, 2, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(dButton)
                                        .addComponent(fillerButton))))
                .addGap(2)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(2)
                .addComponent(instructionsLabel)
                .addComponent(inputPanel)
//                .addComponent(returnButtonPanel)
                .addGap(2, 2, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup()
                        .addComponent(continueButton)
                        .addComponent(aButton)
                        .addComponent(bButton)
                        .addComponent(cButton)
                        .addComponent(dButton))
                .addGap(2, 2, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup()
                        .addComponent(returnButton)
                        .addComponent(fiftyFiftyButton)
                        .addComponent(atAButton)
                        .addComponent(pafButton)
                        .addComponent(fillerButton))
                .addGap(2)
        );

        // Link the sizes of all buttons
        layout.linkSize(SwingConstants.HORIZONTAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, fillerButton);
        layout.linkSize(SwingConstants.VERTICAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, fillerButton);

        // Set action listeners and document filters
        setupInputListenersAndFilters(inputPanel);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        firstNameInput = createFirstNameInput();
        lastNameInput = createLastNameInput();
        inputPanel.add(firstNameInput);
        inputPanel.add(lastNameInput);

        nameSubmitButton = createNameSubmitButton();
        inputPanel.add(nameSubmitButton);

        return inputPanel;
    }

    private void setupInputListenersAndFilters(JPanel inputPanel) {
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

    public JButton createReturnButton(CardLayout cardLayout, JPanel cards) {
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
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return returnButton;
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

    private void updateSubmitButtonState() {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            nameSubmitButton.setEnabled(true);
            fullName = firstName + " " + lastName;
        } else {
            nameSubmitButton.setEnabled(false);
        }
    }

    private JButton createNameSubmitButton() {
        JButton nameSubmitButtonCreate = new JButton("Submit");
        Font submitTextFont = new Font("Arial", Font.BOLD, 10);
        nameSubmitButtonCreate.setPreferredSize(new Dimension(80, 36));

        // Background default colour
        nameSubmitButtonCreate.setBackground(new Color(255, 215, 0));
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
