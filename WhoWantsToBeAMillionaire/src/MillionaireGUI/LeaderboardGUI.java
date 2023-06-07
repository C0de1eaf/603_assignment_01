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

public final class LeaderboardGUI extends JPanel {

    private JButton returnButton;

    public LeaderboardGUI(CardLayout cardLayout, JPanel cards) {

        createReturnButton(cardLayout, cards);
        createLeaderboardTable();

    }

    private void createReturnButton(CardLayout cardLayout, JPanel cards) {

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
            returnButton.setBackground(new Color(100, 150, 255));

            // Add interactivity to return button
            returnButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    returnButton.setBackground(new Color(80, 130, 235));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    returnButton.setBackground(new Color(100, 150, 255));

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    returnButton.setBackground(new Color(60, 110, 215));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    returnButton.setBackground(new Color(100, 150, 255));
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
        setLayout(new GridBagLayout());

        // Create the panel for the top five winners text
        JPanel topFivePanel = new JPanel();
        topFivePanel.setLayout(new GridBagLayout());
        topFivePanel.setOpaque(false);

        // Create the label for the top five winners text
        JLabel topFiveLabel = new JLabel("Top Five Winners");
        topFiveLabel.setFont(new Font("Arial", Font.PLAIN, 42)); // Set the font for the label
        topFivePanel.add(topFiveLabel, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

        // Add the top five panel and the leaderboard panel to the main panel
        add(topFivePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 20, 20, 0), 0, 0));

        // Create leaderboard Panel
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setPreferredSize(new Dimension(600, 600));

        // Set up the JTable model
        String[] columnNames = {"Name", "Prize Money"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        GameDB db = new GameDB();
        ArrayList<String> leaderboardData = getUpdatedLeaderboardData();
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
        leaderboardTable.setRowHeight(60); // Set the height of each row in the table
        leaderboardTable.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        leaderboardTable.setIntercellSpacing(new Dimension(0, 0));

        // Set the header of the table
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setFont(new Font("Arial", Font.PLAIN, 34)); // Set the new font for the header

        leaderboardPanel.add(header);

        // Add leaderboardTable to leaderboardPanel and add leaderboardPanel to the main panel
        leaderboardPanel.add(leaderboardTable);
        add(leaderboardPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.8, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 1, 1));
    }

    private ArrayList<String> getUpdatedLeaderboardData() {
        GameDB db = new GameDB();
        return db.getLeaderboard();
    }
}
