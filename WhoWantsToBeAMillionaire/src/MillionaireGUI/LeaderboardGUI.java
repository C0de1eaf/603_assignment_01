package MillionaireGUI;

import MillionaireDB.GameDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {
        setLayout(new GridBagLayout());

        createReturnButton(cardLayout, cards);
        createLeaderboardTable();

    }

    public void createReturnButton(CardLayout cardLayout, JPanel cards) {
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

        // Add the components to the return button panel
        returnButtonPanel.add(returnButton);

        // Set up the GridBagConstraints for the return button
        GridBagConstraints gbcReturnButtonPanel = new GridBagConstraints(0, 3, 1, 1, 1.0, 0.1, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 20, 20, 0), 0, 0);

        // Add returnButtonPanel to the main panel
        add(returnButtonPanel, gbcReturnButtonPanel);
    }

    public void createLeaderboardTable() {
        // Create the panel for the top five winners text
        JPanel topFivePanel = new JPanel();
        topFivePanel.setLayout(new BoxLayout(topFivePanel, BoxLayout.X_AXIS));
        topFivePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding to the panel

        // Create the label for the top five winners text
        JLabel topFiveLabel = new JLabel("Top Five Winners");
        topFiveLabel.setFont(new Font("Arial", Font.PLAIN, 36)); // Set the font for the label
        topFiveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the label to the top five panel
        topFivePanel.add(topFiveLabel);

        // Set up the GridBagConstraints for the top five panel
        GridBagConstraints gbcTopFive = new GridBagConstraints(0, 1, 1, 1, 1.0, 0.1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(100, 0, 0, 0), 1, 1);

        // Add the top five panel and the leaderboard panel to the main panel
        add(topFivePanel, gbcTopFive);

        /*
         * LEADERBOARD TABLE CONTENT
         */
        // Create leaderboard Panel
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setPreferredSize(new Dimension(550, 550));

        // Set up the JTable model
        String[] columnNames = {"Name", "Prize Money"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        GameDB db = new GameDB();
        ArrayList<String> leaderboardData = db.getLeaderboard();
        leaderboardData.forEach(row -> {
            String[] rowData = row.split(" ");
            String fullName = rowData[0] + " " + rowData[1]; // Concatenate first and last name
            int prizeMoney = Integer.parseInt(rowData[2]); // Parse prize money to an integer
            model.addRow(new Object[]{fullName, prizeMoney});
        });

        class CustomTableCellRenderer extends DefaultTableCellRenderer {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setFont(c.getFont().deriveFont(24.0f)); // Set the new font for the component

                return c;
            }
        }

        // Create the JTable and set its properties
        JTable leaderboardTable = new JTable(model);
        leaderboardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Set the selection mode to single selection
        leaderboardTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer()); // Set the custom renderer for each column
        leaderboardTable.setDefaultEditor(Object.class, null); // Make the JTable non-editable
        leaderboardTable.setFocusable(false); // Disable the JTable's focusable property
        leaderboardTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        leaderboardTable.getTableHeader().setResizingAllowed(false);
        leaderboardTable.setRowHeight(40); // Set the height of each row in the table
        leaderboardTable.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        leaderboardTable.setIntercellSpacing(new Dimension(0, 0));

        // Set up the GridBagConstraints for the leaderboard
        GridBagConstraints gbcLeaderboard = new GridBagConstraints(0, 2, 1, 1, 1.0, 0.8, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 1, 1);

        // Set the header of the table
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setFont(new Font("Arial", Font.PLAIN, 28)); // Set the new font for the header

        leaderboardPanel.add(header);

        // Add leaderboardTable to leaderboardPanel and add leaderboardPanel to the main panel
        leaderboardPanel.add(leaderboardTable);
        add(leaderboardPanel, gbcLeaderboard);
    }
}
