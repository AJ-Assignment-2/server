package clients.customer;

import model.MenuItem.MenuItemTableModel;
import model.MenuItem.MenuItemTotalsTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Imanuel
 */
public class CustomerOrderView extends JFrame {

    private JPanel rootPanel;

    private JPanel customerDetailsPanel;
    private JPanel customerNamePanel;
    private JPanel customerTablePanel;
    private JPanel mealPanel;
    private JPanel buttonPanel;
    private JPanel chooseMenuItemsPanel;

    private JLabel customerNameLabel;
    private JLabel tableNumberLabel;
    private JLabel mealTypeLabel;
    private JLabel foodLabel;
    private JLabel beverageLabel;
    private JLabel applicationTitleLabel;

    private JTextArea nameTextArea;

    private JComboBox tableNumberComboBox;
    private JComboBox<String> foodComboBox;
    private JComboBox<String> beverageComboBox;

    private JRadioButton breakfastRadioButton;
    private JRadioButton lunchRadioButton;
    private JRadioButton dinnerRadioButton;
    private ButtonGroup radioButtonGroup;

    private JScrollPane orderTableScrollPane;

    private JTable orderItemTable;

    private JButton enterDataButton;
    private JButton submitOrderButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton clearDisplayButton;
    private JButton quitButton;

    public CustomerOrderView() {
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        // MARK: Init labels
        customerNameLabel = new JLabel("Customer Name:");
        tableNumberLabel = new JLabel("Table Number:");
        mealTypeLabel = new JLabel("Meal Type");
        foodLabel = new JLabel("Food");
        beverageLabel = new JLabel("Beverage");
        applicationTitleLabel = new JLabel("Welcome to the Restaurant!");
        applicationTitleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        // MARK: Init buttons
        enterDataButton = new JButton("Enter Data");
        displayChoicesButton = new JButton("Display Choices");
        displayOrderButton = new JButton("Display Order");
        clearDisplayButton = new JButton("Clear Display");
        submitOrderButton = new JButton("Submit Order");
        quitButton = new JButton("Exit");

        breakfastRadioButton = new JRadioButton("Breakfast");
        lunchRadioButton = new JRadioButton("Lunch");
        dinnerRadioButton = new JRadioButton("Dinner");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(breakfastRadioButton);
        radioButtonGroup.add(lunchRadioButton);
        radioButtonGroup.add(dinnerRadioButton);

        // MARK: Init tables and scroll panes
        orderItemTable = new JTable(new MenuItemTotalsTableModel(new ArrayList<>()));
        orderTableScrollPane = new JScrollPane(orderItemTable);

        // MARK: Init combo boxes
        tableNumberComboBox = new JComboBox(getTableNumbers());
        foodComboBox = new JComboBox<>();
        beverageComboBox = new JComboBox<>();

        // MARK: Setup customer details panel
        customerDetailsPanel = new JPanel();
        customerDetailsPanel.setLayout(new GridLayout(1, 3));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        customerNamePanel = new JPanel();
        nameTextArea = new JTextArea(1, 10);
        nameTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        customerNamePanel.add(customerNameLabel);
        customerNamePanel.add(nameTextArea);

        customerTablePanel = new JPanel();

        customerTablePanel.add(tableNumberLabel);
        customerTablePanel.add(tableNumberComboBox);

        mealPanel = new JPanel();
        mealPanel.add(mealTypeLabel);
        mealPanel.add(breakfastRadioButton);
        mealPanel.add(lunchRadioButton);
        mealPanel.add(dinnerRadioButton);

        customerDetailsPanel.add(customerNamePanel);
        customerDetailsPanel.add(customerTablePanel);
        customerDetailsPanel.add(mealPanel);

        // MARK: Setup menu item selection panel
        chooseMenuItemsPanel = new JPanel();
        chooseMenuItemsPanel.setLayout(new BoxLayout(chooseMenuItemsPanel, BoxLayout.X_AXIS));

        chooseMenuItemsPanel.add(foodLabel);
        chooseMenuItemsPanel.add(foodComboBox);
        chooseMenuItemsPanel.add(beverageLabel);
        chooseMenuItemsPanel.add(beverageComboBox);

        // MARK: Setup command button area
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 7, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        buttonPanel.add(enterDataButton);
        buttonPanel.add(displayChoicesButton);
        buttonPanel.add(displayOrderButton);
        buttonPanel.add(clearDisplayButton);
        buttonPanel.add(submitOrderButton);
        buttonPanel.add(quitButton);

        // MARK: Attach the panels to the root container and add this container to the JFrame
        rootPanel.add(applicationTitleLabel);
        rootPanel.add(customerDetailsPanel);
        rootPanel.add(chooseMenuItemsPanel);
        rootPanel.add(orderTableScrollPane);
        rootPanel.add(buttonPanel);

        add(rootPanel);
    }

    public String[] getTableNumbers() {
        int maxTableNumbers = 12;
        String[] tableNumbers = new String[maxTableNumbers + 1];
        tableNumbers[0] = "Select a table number";
        for(int i = 1; i <= 12; i++) {
            tableNumbers[i] = Integer.toString(i);
        }
        return tableNumbers;
    }
    
    public void addBreakfastRadioButtonListener(ActionListener breakfastRadioButtonListener) {
        breakfastRadioButton.addActionListener(breakfastRadioButtonListener);
    }

    public void addLunchRadioButtonListener(ActionListener lunchRadioButtonListener) {
        lunchRadioButton.addActionListener(lunchRadioButtonListener);
    }

    public void addDinnerRadioButtonListener(ActionListener dinnerRadioButtonListener) {
        dinnerRadioButton.addActionListener(dinnerRadioButtonListener);
    }

    public void addEnterDataButtonListener(ActionListener enterDataButtonListener) {
        enterDataButton.addActionListener(enterDataButtonListener);
    }

    public void addDisplayChoicesButtonListener(ActionListener displayChoicesButtonListener) {
        displayChoicesButton.addActionListener(displayChoicesButtonListener);
    }

    public void addDisplayOrderButtonListener(ActionListener displayOrderButtonListener) {
        displayOrderButton.addActionListener(displayOrderButtonListener);
    }

    public void addClearDisplayButtonListener(ActionListener clearDisplayButtonListener) {
        clearDisplayButton.addActionListener(clearDisplayButtonListener);
    }
    
    public void addSubmitOrderButtonListener(ActionListener submitOrderButtonListener) {
        submitOrderButton.addActionListener(submitOrderButtonListener);
    }

    public void addQuitButtonListener(ActionListener quitButtonListener) {
        quitButton.addActionListener(quitButtonListener);
    }

    public String getCustomerName() {
        return nameTextArea.getText();
    }

    public String getCustomerTable() {
        return tableNumberComboBox.getSelectedItem().toString();
    }

    public JComboBox<String> getFoodComboBox() {
        return foodComboBox;
    }

    public JComboBox<String> getBeverageComboBox() {
        return beverageComboBox;
    }

    public JRadioButton getBreakfastRadioButton() {
        return breakfastRadioButton;
    }

    public void showErrorDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.ERROR_MESSAGE);
    }

    public JComboBox getTableNumberComboBox() {
        return tableNumberComboBox;
    }

    public JTextArea getNameTextArea() {
        return nameTextArea;
    }

    public JTable getOrderItemTable() {
        return orderItemTable;
    }
}
