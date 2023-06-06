package MillionaireGUI;

import MillionaireDB.GameDB;
import WhoWantsToBeAMillionaire.Question;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public final class GameGUI extends JPanel {

    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JButton nameSubmitButton;
    private String fullName;
    private JButton returnButton;
    private JLabel instructionsLabel;
    private GameDB gameDatabase;
    private List<ArrayList<Question>> questions;
    public int MAX_CHARS = 18;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public GameGUI(CardLayout cardLayout, JPanel cards) {
        createGUI(cardLayout, cards);
    }

    public void createGUI(CardLayout cardLayout, JPanel cards) {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        this.gameDatabase = new GameDB();
        gameDatabase.createConnection();
        ArrayList<Question> easy = gameDatabase.getEasyQuestions();
        ArrayList<Question> hard = gameDatabase.getHardQuestions();
        this.questions = Arrays.asList(easy, hard);

        UIManager.put("Button.disabledText", new Color(200, 200, 200)); // Set the text color for disabled buttons
        UIManager.put("Button.disabled", new Color(100, 100, 100)); // Set the background color for disabled buttons

        // Create input panel with input fields and button
        JPanel inputPanel = createInputPanel();

        // Button texts
        String[] buttonTexts = {
            "Continue", "Option A", "Option B", "Option C", "Option D",
            "50 - 50", "AtA", "PaF", "Filler"
        };

        // Set preferred button size
        Dimension buttonSize = new Dimension(220, 220);

        // Create buttons and store them in a List
        List<JButton> buttons = new ArrayList<>();
        for (String buttonText : buttonTexts) {
            JButton button = new JButton(buttonText);
            button.setPreferredSize(buttonSize);
            button.setEnabled(false);
            buttons.add(button);

            setColourOfButton(button);
        }

        inputPanel.add(nameSubmitButton);
        // Replace the specific buttons with the ones from the list
        JButton continueButton = buttons.get(0);
        JButton aButton = buttons.get(1);
        JButton bButton = buttons.get(2);
        JButton cButton = buttons.get(3);
        JButton dButton = buttons.get(4);
        JButton fiftyFiftyButton = buttons.get(5);
        returnButton = createReturnButton(cardLayout, cards, inputPanel);
        JButton atAButton = buttons.get(6);
        JButton pafButton = buttons.get(7);
        JButton fillerButton = buttons.get(8);

        // Add instructions label
        instructionsLabel = createInstructionsLabel();
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set GroupLayout's horizontal and vertical groups
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(instructionsLabel)
                        .addComponent(inputPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(continueButton)
                                        .addComponent(returnButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(aButton)
                                        .addComponent(fiftyFiftyButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(bButton)
                                        .addComponent(atAButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(cButton)
                                        .addComponent(pafButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(dButton)
                                        .addComponent(fillerButton))))
        );

        // All of the buttons anchored to the bottom with proper spacing in between
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(instructionsLabel)
                .addComponent(inputPanel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(continueButton)
                        .addComponent(aButton)
                        .addComponent(bButton)
                        .addComponent(cButton)
                        .addComponent(dButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(returnButton)
                        .addComponent(fiftyFiftyButton)
                        .addComponent(atAButton)
                        .addComponent(pafButton)
                        .addComponent(fillerButton))
        );

        // Link the sizes of all buttons
        layout.linkSize(SwingConstants.HORIZONTAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, fillerButton);
        layout.linkSize(SwingConstants.VERTICAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, fillerButton);

        nameSubmitButton.addActionListener((ActionEvent e) -> {
            setFullName(firstNameInput.getText() + " " + lastNameInput.getText());
            updateIntroductionText();
            cardLayout.show(cards, "gameGUI");
            continueButton.setEnabled(true); // Enable the continue button
        });

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
                inputPanel.setVisible(false);
                nameSubmitButton.setVisible(false);
                instructionsLabel.setVisible(true);
            } else {
                firstNameInput.setText("");  // Clear the text field
                lastNameInput.setText("");  // Clear the text field
                updateSubmitButtonState();
            }
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
                if (newLength <= MAX_CHARS) {
                    super.replace(fb, offset, length, newText, attrs);
                }
                updateSubmitButtonState();
            }
        };
    }

    private JButton createReturnButton(CardLayout cardLayout, JPanel cards, JPanel inputPanel) {
        /*
         * RETURN BUTTON CONTENT
         */

        // Create the return button
        try {
            BufferedImage panelImage = ImageIO.read(new File("resources/cropped_return.png"));
            ImageIcon returnButtonIcon = new ImageIcon(panelImage);

            returnButton = new JButton(returnButtonIcon);

            // Add text to the right of the image
            returnButton.setText("Return");

            Font returnButtonFont = new Font("Arial", Font.BOLD, 26);
            returnButton.setFont(returnButtonFont);

            // Add space between the image and the text
            returnButton.setIconTextGap(10);

            // Add ActionListener to return button
            returnButton.addActionListener(e -> {
                // Reset values and components
                firstNameInput.setText("");
                lastNameInput.setText("");
                fullName = null;
                nameSubmitButton.setEnabled(false);
                inputPanel.setVisible(true);
                nameSubmitButton.setVisible(true);
                instructionsLabel.setVisible(false);

                // Show menuGUI
                cardLayout.show(cards, "menuGUI");
            });

            // Set the default colour scheme of the button
            setColourOfButton(returnButton);

        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Set margins for the return button
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
        JLabel instructionsLabelCreate = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 28);
        instructionsLabelCreate.setFont(font);
        instructionsLabelCreate.setVisible(false);
        return instructionsLabelCreate;
    }

    private void updateIntroductionText() {
        instructionsLabel.setText("<html><body><center>Welcome <b><i>" + fullName + "</i></b> to the Who Wants To Be A Millionaire game.<br/>"
                + "You will be asked a total of 10 questions with varying <br/>"
                + "difficulty as the questions go on. Please answer them by<br/>"
                + "clicking on the correct answer in order to move on.</center></body></html>");
    }

    public void setColourOfButton(JButton button) {
        // Set font for the button
        Font returnButtonFont = new Font("Arial", Font.BOLD, 26);
        button.setFont(returnButtonFont);

        // Background default colour
        if (button.isEnabled()) {
            button.setBackground(new Color(100, 150, 255));
        } else {
            button.setBackground(new Color(180, 180, 180));
        }

        // Add interactivity to return button
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(80, 130, 235));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(100, 150, 255));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(60, 110, 215));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(100, 150, 255));
                }
            }
        });
    }

}
