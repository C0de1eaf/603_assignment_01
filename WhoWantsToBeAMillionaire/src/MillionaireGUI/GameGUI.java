package MillionaireGUI;

import LifeLine.*;
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
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public final class GameGUI extends JPanel implements SystemInit {

    private boolean isCorrect;
    private int currentLevel;
    private final int[] cashPrize;
    private String[] answers;
    private int currentCashPrize;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JButton nameSubmitButton;
    private String fullName;
    private JButton returnButton;
    private JLabel mainLabel;
    private GameDB gameDatabase;
    private List<ArrayList<Question>> questions;
    private Question currentQuestion;
    private User newUser;
    public int MAX_CHARS = 18;

    private final JPanel mainLabelPanel;
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
    private final Leaderboard leaderboard;

    private AskTheAudienceGUI LifelineAtA;

    public GameGUI(CardLayout cardLayout, JPanel cards) {
        inputPanel = createInputPanel();
        String[] buttonTexts = {
            "Continue", "A", "B", "C", "D",
            "50 - 50", "AtA", "PaF", "Game Info Panel"
        };
        Dimension buttonSize = new Dimension(300, 120);
        List<JButton> buttons = createButtons(buttonTexts, buttonSize);
        continueButton = buttons.get(0);
        aButton = buttons.get(1);
        bButton = buttons.get(2);
        cButton = buttons.get(3);
        dButton = buttons.get(4);
        fiftyFiftyButton = buttons.get(5);
        atAButton = buttons.get(6);
        pafButton = buttons.get(7);
        currentScoreAndLevelButton = buttons.get(8);
        mainLabelPanel = new JPanel();
        leaderboard = new Leaderboard();
        inputPanel.add(nameSubmitButton);
        returnButton = createReturnButton(cardLayout, cards, inputPanel);

        cashPrize = new int[]{0, 100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000};
        currentCashPrize = cashPrize[0];

        createGUI(cardLayout, cards);
    }

    @Override
    public void createGUI(CardLayout cardLayout, JPanel cards) {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        resetToDefault();

        mainLabelPanel.setSize(1124, 300);
        mainLabelPanel.setPreferredSize(new Dimension(1124, 300));
        mainLabelPanel.setLayout(new BorderLayout());
        mainLabel = createMainLabel();
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabelPanel.add(mainLabel, BorderLayout.CENTER);

        // Set GroupLayout's horizontal and vertical groups
        setupHorizontalGroup(layout);

        // All of the buttons anchored to the bottom with proper spacing in between
        setupVerticalGroup(layout);

        // Link the sizes of all buttons
        layout.linkSize(SwingConstants.HORIZONTAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, currentScoreAndLevelButton);
        layout.linkSize(SwingConstants.VERTICAL, continueButton, aButton, bButton, cButton, dButton, returnButton, fiftyFiftyButton, atAButton, pafButton, currentScoreAndLevelButton);

        aButton.addActionListener((ActionEvent e) -> {
            isCorrect = checkAnswerCorrect(0);
            try {
                showIntervalScreen(isCorrect);
            } catch (IOException ex) {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        nameSubmitButton.addActionListener((ActionEvent e) -> {
            handleNameSubmitButtonAction();
        });

        continueButton.addActionListener((ActionEvent e) -> {
            handleContinueButtonAction();
        });

        aButton.addActionListener((ActionEvent e) -> {
            handleAnswerButtonAction(0);
        });

        bButton.addActionListener((ActionEvent e) -> {
            handleAnswerButtonAction(1);
        });

        cButton.addActionListener((ActionEvent e) -> {
            handleAnswerButtonAction(2);
        });

        dButton.addActionListener((ActionEvent e) -> {
            handleAnswerButtonAction(3);
        });
        // Set action listeners and document filters
        setupInputListenersAndFilters(inputPanel);
    }

    private void handleNameSubmitButtonAction() {
        fullName = (firstNameTextField.getText() + " " + lastNameTextField.getText());
        newUser = new User(fullName);
        updateMainLabel();
        continueButton.setEnabled(true);
        continueButton.setText("Continue");
        returnButton.setEnabled(true);
        returnButton.setText("Return");
        currentScoreAndLevelButton.setText("Level " + currentLevel + " | Reward $" + currentCashPrize);

        setColourOfButton(continueButton);
        setColourOfButton(returnButton);
    }

    private void handleContinueButtonAction() {
        returnButton.setEnabled(false);
        continueButton.setEnabled(false);

        aButton.setEnabled(true);
        bButton.setEnabled(true);
        cButton.setEnabled(true);
        dButton.setEnabled(true);

        currentQuestion = getRandomQuestion();
        mainLabel.setText(currentQuestion.getQuestion());
        answers = currentQuestion.getAnswers();
        aButton.setText("A) " + answers[0]);
        bButton.setText("B) " + answers[1]);
        cButton.setText("C) " + answers[2]);
        dButton.setText("D) " + answers[3]);

        setColourOfButton(continueButton);
        setColourOfButton(returnButton);
        setColourOfButton(aButton);
        setColourOfButton(bButton);
        setColourOfButton(cButton);
        setColourOfButton(dButton);
    }

    private void handleAnswerButtonAction(int answerIndex) {
        isCorrect = checkAnswerCorrect(answerIndex);
        try {
            showIntervalScreen(isCorrect);
        } catch (IOException ex) {
            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<JButton> createButtons(String[] buttonTexts, Dimension buttonSize) {
        List<JButton> buttons = new ArrayList<>();
        for (String buttonText : buttonTexts) {
            JButton button = new JButton(buttonText);
            button.setPreferredSize(buttonSize);
            button.setEnabled(false);
            buttons.add(button);
            setColourOfButton(button);
        }
        return buttons;
    }

    @Override
    public JPanel createInputPanel() {
        JPanel inputPanelReturn = new JPanel(new FlowLayout(FlowLayout.CENTER));

        firstNameTextField = createInput("First Name");
        lastNameTextField = createInput("Last Name");
        firstNameLabel = createLabel("First Name");
        lastNameLabel = createLabel("Last Name");

        // Add the JLabels to the input panel
        inputPanelReturn.add(firstNameLabel);
        inputPanelReturn.add(firstNameTextField);
        inputPanelReturn.add(lastNameLabel);
        inputPanelReturn.add(lastNameTextField);

        nameSubmitButton = createSubmitButton();
        inputPanelReturn.add(nameSubmitButton);

        return inputPanelReturn;
    }

    private void setupInputListenersAndFilters(JPanel inputPanel) {
        firstNameTextField.addActionListener(e -> {
            fullName = firstNameTextField.getText();
            if (!fullName.isEmpty()) {
            }
            firstNameTextField.setText("");  // Clear the text field
            updateSubmitButtonState();
        });

        nameSubmitButton.addActionListener(e -> {
            if (fullName != null) {
                inputPanel.setVisible(false);
                nameSubmitButton.setVisible(false);
                mainLabel.setVisible(true);
            } else {
                updateSubmitButtonState();
            }
        });

        // Set document filters for the text fields
        ((AbstractDocument) firstNameTextField.getDocument()).setDocumentFilter(createDocumentFilter());
        ((AbstractDocument) lastNameTextField.getDocument()).setDocumentFilter(createDocumentFilter());
    }

    @Override
    public DocumentFilter createDocumentFilter() {
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
                if (!(currentCashPrize == cashPrize[0])) {
                    // Show confirmation dialog
                    int response = JOptionPane.showConfirmDialog(null, "<html>You are leaving the game<br>If you leave you recieve $" + currentCashPrize + " under your name (" + fullName + ")<br>You will not be able to resume",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        try {
                            // Reset values and components
                            firstNameTextField.setText("");
                            lastNameTextField.setText("");
                            fullName = null;
                            nameSubmitButton.setEnabled(false);
                            inputPanel.setVisible(true);
                            nameSubmitButton.setVisible(true);
                            mainLabel.setText("");
                            updateLeaderboardData();
                            resetToDefault();
                            // Show menuGUI
                            cardLayout.show(cards, "menuGUI");
                            updateButtons();
                        } catch (IOException ex) {
                            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else {
                    try {
                        updateLeaderboardData();
                        resetToDefault();
                        cardLayout.show(cards, "menuGUI");
                    } catch (IOException ex) {
                        Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

            // Set the default colour scheme of the button
            returnButton.setEnabled(false);
            setColourOfButton(returnButton);

        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Set margins for the return button
        return returnButton;
    }

    private JTextField createInput(String toolTipText) {
        JTextField textField = new JTextField(20);
        textField.setToolTipText("<html><b><font color=grey>"
                + "Enter your <b>" + toolTipText + "</b> here" + "</font></b></html>");
        textField.setPreferredSize(new Dimension(0, 37));
        return textField;
    }

    private JLabel createLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        Font font = new Font("Arial", Font.PLAIN, 20);
        label.setFont(font);
        return label;
    }

    private void updateSubmitButtonState() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            nameSubmitButton.setEnabled(true);
            fullName = firstName + " " + lastName;

        } else {
            nameSubmitButton.setEnabled(false);
        }
    }

    private JButton createSubmitButton() {
        JButton submitButton = new JButton("Submit");
        Font submitTextFont = new Font("Arial", Font.BOLD, 12);
        submitButton.setPreferredSize(new Dimension(80, 36));

        // Background default color
        setColourOfButton(submitButton);
        submitButton.setEnabled(false);
        submitButton.setFont(submitTextFont);
        return submitButton;
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
        button.setEnabled(false);
    }

    @Override
    public Question getRandomQuestion() {
        Random rand = new Random();
        currentLevel++;
        System.out.println("\n--Current Level: " + currentLevel + "--\n"); // prints out current level
        int questionIndex = rand.nextInt(questions.get(currentLevel < 10 ? 0 : 1).size()); // get random question randomly based on the size of the array
        ArrayList<Question> questionList = questions.get(currentLevel < 10 ? 0 : 1); // create a new ArrayList variable based on the level difficulty
        Question selectedQuestion = questionList.get(questionIndex);

        questionList.remove(questionIndex); // remove the chosen question from the ArrayList

        for (int i = 0; i < selectedQuestion.getAnswers().length; i++) {
            System.out.println(selectedQuestion.getAnswers()[i]);
        }
        selectedQuestion.getCorrectAnswerIndex();

        return selectedQuestion;
    }

    private boolean checkAnswerCorrect(int selectedAnswerIndex) {
        return selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex();
    }

    private void showIntervalScreen(boolean isCorrect) throws IOException {
        if (isCorrect) {
            handleAnswer(true);
        } else {
            handleAnswer(false);
        }
        updateButtons();
    }

    @Override
    public void updateLeaderboardData() throws IOException {
        ArrayList<String> leaderboardData = new GameDB().getLeaderboard();
        int lowestValue = leaderboardData.stream()
                .mapToInt(row -> Integer.parseInt(row.split(" ")[2]))
                .min().orElse(Integer.MAX_VALUE);

        storeUserScoreAndUpdateLeaderboard(lowestValue);
    }

    private void handleAnswer(boolean isCorrect) {
        String message;
        if (isCorrect) {
            currentCashPrize = cashPrize[currentLevel];
            message = currentLevel == 15
                    ? "<html><center><b>Congratulations! You won the game!</b><br><br>"
                    + fullName + " has answered all 15 questions correctly and won <b>$"
                    + currentCashPrize + "</b>!</center></html>"
                    : "<html><center><b>Correct!<br><br>"
                    + "</b>Click <b>Continue</b> to go to the next level to try and win <b>$"
                    + cashPrize[currentLevel + 1] + "</b> or<br>"
                    + "Click <b>Return</b> to exit the game with your current prize money of <b>$"
                    + currentCashPrize + "</b>.</center></html>";

            // Disable continueButton if currentLevel reaches 15
            continueButton.setEnabled(currentLevel != 15);
        } else {
            currentCashPrize = cashPrize[0];
            message = "<html><center><b>Incorrect!</b><br><br>"
                    + "Unfortunately you lose, no prize money is gained.<br>"
                    + "Click <b>Return</b> and <b>Play</b> again to try and win!</center></html>";
            continueButton.setEnabled(false);
        }
        mainLabel.setText(message);
        returnButton.setEnabled(true);
    }

    private void updateButtons() {
        currentScoreAndLevelButton.setText("<html><center>Level :" + currentLevel + " | Reward: $" + currentCashPrize + "</center></html>");
        Stream.of(aButton, bButton, cButton, dButton).forEach(button -> {
            button.setEnabled(false);
            setColourOfButton(button);
        });
        setColourOfButton(continueButton);
        setColourOfButton(returnButton);
    }

    @Override
    public void storeUserScoreAndUpdateLeaderboard(int lowestValue) throws IOException {
        newUser.update(currentCashPrize);
        if (currentCashPrize > lowestValue) {
            leaderboard.updateLeaderboard(newUser, currentCashPrize);
        } else {
            leaderboard.updateLeaderboard(newUser, currentCashPrize);

        }
    }

    private void setupHorizontalGroup(GroupLayout layout) {
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(mainLabelPanel)
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
    }

    private void setupVerticalGroup(GroupLayout layout) {
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
    }

    // Setup default value 
    public void resetToDefault() {
        currentLevel = 0;
        currentCashPrize = cashPrize[0];
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        nameSubmitButton.setVisible(true);
        inputPanel.setVisible(true);
        gameDatabase = new GameDB();
        gameDatabase.createConnection();
        ArrayList<Question> easy = gameDatabase.getEasyQuestions();
        ArrayList<Question> hard = gameDatabase.getHardQuestions();
        questions = Arrays.asList(easy, hard);
        resetButtonToDefault(continueButton);
        resetButtonToDefault(aButton);
        resetButtonToDefault(bButton);
        resetButtonToDefault(cButton);
        resetButtonToDefault(dButton);
        resetButtonToDefault(returnButton);
        resetButtonToDefault(fiftyFiftyButton);
        resetButtonToDefault(atAButton);
        resetButtonToDefault(pafButton);
        resetButtonToDefault(currentScoreAndLevelButton);
    }

    public void resetButtonToDefault(JButton button) {
        button.setBackground(new Color(180, 180, 180));
        button.setEnabled(false);
        button.setText("");
    }
}
