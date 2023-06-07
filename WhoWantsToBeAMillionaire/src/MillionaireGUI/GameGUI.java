package MillionaireGUI;

import MillionaireDB.GameDB;
import MillionaireDB.Leaderboard;
import MillionaireDB.User;
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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public final class GameGUI extends JPanel {

    private boolean isCorrect;
    private int currentLevel;
    private final int[] cashPrize;
    private int currentCashPrize;
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JButton nameSubmitButton;
    private String fullName;
    private JButton returnButton;
    private JLabel mainLabel;
    private GameDB gameDatabase;
    private List<ArrayList<Question>> questions;
    private Question currentQuestion;
    public int MAX_CHARS = 18;

    private final JPanel inputPanel;
    private final JButton continueButton;
    private final JButton aButton;
    private final JButton bButton;
    private final JButton cButton;
    private final JButton dButton;
    private final JButton fiftyFiftyButton;
    private final JButton atAButton;
    private final JButton pafButton;
    private final JButton currentScoreAndLevelButton;
    private User newUser;
    private final Leaderboard leaderboard;

    public GameGUI(CardLayout cardLayout, JPanel cards) {
        // Create input panel with input fields and button
        inputPanel = createInputPanel();

        // Button texts
        String[] buttonTexts = {
            "Continue", "Option A", "Option B", "Option C", "Option D",
            "50 - 50", "AtA", "PaF", "Game Info Panel"
        };

        // Set preferred button size
        Dimension buttonSize = new Dimension(300, 220);

        // Create buttons and store them in a List
        List<JButton> buttons = new ArrayList<>();
        for (String buttonText : buttonTexts) {
            JButton button = new JButton(buttonText);
            button.setPreferredSize(buttonSize);
            button.setEnabled(false);
            buttons.add(button);

            setColourOfButton(button);
        }
        leaderboard = new Leaderboard();
        inputPanel.add(nameSubmitButton);
        // Replace the specific buttons with the ones from the list
        continueButton = buttons.get(0);
        aButton = buttons.get(1);
        bButton = buttons.get(2);
        cButton = buttons.get(3);
        dButton = buttons.get(4);
        returnButton = createReturnButton(cardLayout, cards, inputPanel);
        fiftyFiftyButton = buttons.get(5);
        atAButton = buttons.get(6);
        pafButton = buttons.get(7);
        currentScoreAndLevelButton = buttons.get(8);
        this.cashPrize = new int[]{100, 200, 300, 500, 1000, 5000, 10000, 50000, 100000, 250000};
        currentCashPrize = 0;
        createGUI(cardLayout, cards);
    }

    public void createGUI(CardLayout cardLayout, JPanel cards) {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        // Create Database stuff.
        this.gameDatabase = new GameDB();
        gameDatabase.createConnection();
        ArrayList<Question> easy = gameDatabase.getEasyQuestions();
        ArrayList<Question> hard = gameDatabase.getHardQuestions();
        this.questions = Arrays.asList(easy, hard);
        currentLevel = 1;

        UIManager.put("Button.disabledText", new Color(200, 200, 200)); // Set the text color for disabled buttons
        UIManager.put("Button.disabled", new Color(100, 100, 100)); // Set the background color for disabled buttons

        // Create a new JPanel for the mainLabel
        JPanel mainLabelPanel = new JPanel();
        mainLabelPanel.setSize(1124, 300);
        mainLabelPanel.setPreferredSize(new Dimension(1124, 300));
        mainLabelPanel.setLayout(new BorderLayout());

        // Add instructions label
        mainLabel = createMainLabel();
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the mainLabel to the mainLabelPanel
        mainLabelPanel.add(mainLabel, BorderLayout.CENTER);

        // Set GroupLayout's horizontal and vertical groups
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(mainLabelPanel) // Replace mainLabel with mainLabelPanel
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
                                        .addComponent(currentScoreAndLevelButton))))
        );

        // All of the buttons anchored to the bottom with proper spacing in between
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(mainLabelPanel)
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
                        .addComponent(currentScoreAndLevelButton))
        );

        // Link the sizes of all buttons
        layout.linkSize(SwingConstants.HORIZONTAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, currentScoreAndLevelButton);
        layout.linkSize(SwingConstants.VERTICAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, currentScoreAndLevelButton);

        nameSubmitButton.addActionListener((ActionEvent e) -> {
            fullName = (firstNameInput.getText() + " " + lastNameInput.getText());
            System.out.println(fullName);
            newUser = new User(fullName);
            updateMainLabel();
            cardLayout.show(cards, "gameGUI");
            continueButton.setEnabled(true); // Enable the continue button
            setColourOfButton(continueButton); // Update the color of the continue button
        });

        continueButton.addActionListener((ActionEvent e) -> {
            returnButton.setEnabled(false);
            continueButton.setEnabled(false);
            currentQuestion = getRandomQuestion();
            mainLabel.setText(currentQuestion.getQuestion());
            String[] answers = currentQuestion.getAnswers();
            aButton.setText("A) " + answers[0]);
            bButton.setText("B) " + answers[1]);
            cButton.setText("C) " + answers[2]);
            dButton.setText("D) " + answers[3]);
            aButton.setEnabled(true);
            bButton.setEnabled(true);
            cButton.setEnabled(true);
            dButton.setEnabled(true);
            // Set up the currentScoreAndLevelButton values
            currentScoreAndLevelButton.setEnabled(true);
            setColourOfScoreAndPrizeButton(currentScoreAndLevelButton);

            currentScoreAndLevelButton.setText("<html><center><font color='black'>Level :" + currentLevel + " | Reward: $" + currentCashPrize + "</font></center></html>");

            // Update the color of the buttons
            setColourOfButton(continueButton);
            setColourOfButton(returnButton);
            setColourOfButton(aButton);
            setColourOfButton(bButton);
            setColourOfButton(cButton);
            setColourOfButton(dButton);
        });

        aButton.addActionListener((ActionEvent e) -> {
            isCorrect = checkAnswerCorrect(0);
            try {
                showIntervalScreen(isCorrect);
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bButton.addActionListener((ActionEvent e) -> {
            isCorrect = checkAnswerCorrect(1);
            try {
                showIntervalScreen(isCorrect);
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cButton.addActionListener((ActionEvent e) -> {
            isCorrect = checkAnswerCorrect(2);
            try {
                showIntervalScreen(isCorrect);
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        dButton.addActionListener((ActionEvent e) -> {
            isCorrect = checkAnswerCorrect(3);
            try {
                showIntervalScreen(isCorrect);
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Set action listeners and document filters
        setupInputListenersAndFilters(inputPanel);
    }

    private JPanel createInputPanel() {
        JPanel inputPanelReturn = new JPanel(new FlowLayout(FlowLayout.CENTER));

        firstNameInput = createFirstNameInput();
        lastNameInput = createLastNameInput();
        JLabel firstNameLabel = createFirstNameLabel();  // Initialize firstNameLabel
        JLabel lastNameLabel = createLastNameLabel();    // Initialize lastNameLabel

        // Add the JLabels to the input panel
        inputPanelReturn.add(firstNameLabel);
        inputPanelReturn.add(firstNameInput);
        inputPanelReturn.add(lastNameLabel);
        inputPanelReturn.add(lastNameInput);

        nameSubmitButton = createNameSubmitButton();
        inputPanelReturn.add(nameSubmitButton);

        return inputPanelReturn;
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
                mainLabel.setVisible(true);
            } else {
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
                mainLabel.setText("");

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
                + "Enter your <b>first name</b> here" + "</font></b></html>");
        firstNameTextField.setPreferredSize(new Dimension(0, 37));
        return firstNameTextField;
    }

    private JTextField createLastNameInput() {
        JTextField lastNameTextField = new JTextField(20);
        lastNameTextField.setToolTipText("<html><b><font color=grey>"
                + "Enter your <b>last name</b> here" + "</font></b></html>");
        lastNameTextField.setPreferredSize(new Dimension(0, 37));
        return lastNameTextField;
    }

    private JLabel createFirstNameLabel() {
        JLabel firstNameLabel = new JLabel("First Name");
        Font fnFont = new Font("Arial", Font.PLAIN, 20);
        firstNameLabel.setFont(fnFont);
        return firstNameLabel;
    }

    private JLabel createLastNameLabel() {
        JLabel lastNameLabel = new JLabel("Last Name");
        Font lnFont = new Font("Arial", Font.PLAIN, 20);
        lastNameLabel.setFont(lnFont);
        return lastNameLabel;
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
        Font submitTextFont = new Font("Arial", Font.BOLD, 12);
        nameSubmitButtonCreate.setPreferredSize(new Dimension(80, 36));

        // Background default colour
        setColourOfButton(nameSubmitButtonCreate);
        nameSubmitButtonCreate.setEnabled(false);
        nameSubmitButtonCreate.setFont(submitTextFont);
        return nameSubmitButtonCreate;
    }

    private JLabel createMainLabel() {
        JLabel mainLabelCreate = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 28);
        mainLabelCreate.setFont(font);
        mainLabelCreate.setVisible(false);
        return mainLabelCreate;
    }

    private void updateMainLabel() {
        mainLabel.setText("<html><div style='text-align: center;'>"
                + "Welcome <b><i>" + fullName + "</i></b> to the Who Wants To Be A Millionaire game.<br/>"
                + "You will be asked a total of 10 questions with varying <br/>"
                + "difficulty as the questions go on. Please answer them by<br/>"
                + "clicking on the correct answer in order to move on.<br/>"
                + "Once the question is asked, you will not be able to leave the <br/>"
                + "game but you will be able to quit any time before every round <br/>"
                + "starts and keep all of your current money winnings</div></html>");
    }

    public void setColourOfButton(JButton button) {
        // Set font for the button
        Font returnButtonFont = new Font("Arial", Font.BOLD, 20);
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

    public void setColourOfScoreAndPrizeButton(JButton button) {
        // Background default colour
        if (button.isEnabled()) {
            button.setBackground(new Color(197, 148, 175));
        }
        // Add interactivity to return button
        button.setEnabled(false);
    }

    public Question getRandomQuestion() {
        Random rand = new Random();
        System.out.println("\n--Current Level: " + currentLevel + "--\n"); // prints out current level
        int questionIndex = rand.nextInt(questions.get(currentLevel < 7 ? 0 : 1).size()); // get random question randomly based on the size of the array
        ArrayList<Question> questionList = questions.get(currentLevel < 7 ? 0 : 1); // create a new ArrayList variable based on the level difficulty
        Question selectedQuestion = questionList.get(questionIndex);

        questionList.remove(questionIndex); // remove the chosen question from the ArrayList

        return selectedQuestion;
    }

    private boolean checkAnswerCorrect(int selectedAnswerIndex) {
        return selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex();
    }

    private void showIntervalScreen(boolean isCorrect) throws IOException {
        ArrayList<String> leaderboardData = getUpdatedLeaderboardData();

        int lowestValue = Integer.MAX_VALUE;
        for (String row : leaderboardData) {
            String[] rowData = row.split(" ");
            int playerValue = Integer.parseInt(rowData[2]);
            if (playerValue < lowestValue) {
                lowestValue = playerValue;
            }
        }

        if (isCorrect) {
            if (currentLevel == 10) {
                // Check if the player has answered 10 questions correctly
                mainLabel.setText("<html><center><b>Congratulations! You won the game!</b><br><br>"
                        + fullName + " has answered all 10 questions correctly and won <b>$" + cashPrize[currentLevel - 1] + "</b>!</center></html>");
                continueButton.setEnabled(false); // Disable the continue button
                returnButton.setEnabled(true);

            } else {
                currentCashPrize = cashPrize[currentLevel - 1]; // Update the cash prize
                mainLabel.setText("<html><center><b>Correct!<br><br>"
                        + "</b>Click <b>Continue</b> to go to the next level to try and win <b>$" + cashPrize[currentLevel] + "</b> or<br>"
                        + "Click <b>Return</b> to exit the game with your current prize money of <b>$" + cashPrize[currentLevel - 1] + "</b>.</center></html>");
                continueButton.setEnabled(true);
                returnButton.setEnabled(true);
            }

        } else {
            mainLabel.setText("<html><center><b>Incorrect!</b><br><br>"
                    + "Unfortunately you lose, no prize money is gained.<br>"
                    + "Click <b>Return</b> and <b>Play</b> again to try and win!</center></html>");
            continueButton.setEnabled(false);
            returnButton.setEnabled(true);
        }

        // Update the currentScoreAndLevelButton values
        currentScoreAndLevelButton.setText("<html><center><font colour='black'>Level :" + currentLevel + " | Reward: $" + currentCashPrize + "</font></center></html>");

        // Store the user's name and score in the database
        newUser.update(currentCashPrize);
        System.out.println(newUser.userExists());
        System.out.println(newUser.getName() + " won " + currentCashPrize);

        // Update the leaderboard with the user's name and score
        if (currentCashPrize > lowestValue) {
            leaderboard.updateLeaderboard(newUser, currentCashPrize);
        }

        // Disable all answer buttons
        aButton.setEnabled(false);
        bButton.setEnabled(false);
        cButton.setEnabled(false);
        dButton.setEnabled(false);

        // Reset the colour of option buttons
        setColourOfButton(aButton);
        setColourOfButton(bButton);
        setColourOfButton(cButton);
        setColourOfButton(dButton);

        // Update the color of the buttons
        setColourOfButton(continueButton);
        setColourOfButton(returnButton);
        currentLevel++;
    }

    private ArrayList<String> getUpdatedLeaderboardData() {
        GameDB db = new GameDB();
        return db.getLeaderboard();
    }
}
