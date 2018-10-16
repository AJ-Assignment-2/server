package clients.customer;

import model.MenuItem.MenuItemTotalsTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * The customer view comprises of a section that allows a customer to enter their name, table number and meal time.
 * The customer can select menu items and add them to an order, this order can be submitted to the server.
 */
public class CustomerOrderView extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;
    
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

    private JTable orderItemTable;
    private JScrollPane orderTableScrollPane;

    
    private JButton enterDataButton;
    private JButton submitOrderButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton clearDisplayButton;
    private JButton quitButton;

    /**
     * Initialise the different components used for the customer view.
     */
    public CustomerOrderView() {
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        this.menuAbout = new JMenuItem("About");
        menuFile.add(menuAbout);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);
        
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
        applicationTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        applicationTitleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        // MARK: Init buttons
        enterDataButton = new JButton("Enter Data");
        displayChoicesButton = new JButton("Display Choices");
        displayOrderButton = new JButton("Display Order");
        clearDisplayButton = new JButton("Clear Display");
        clearDisplayButton.setEnabled(false);
        submitOrderButton = new JButton("Submit Order");
        submitOrderButton.setEnabled(false);
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
        orderTableScrollPane.setBorder(BorderFactory.createTitledBorder("Menu Choices and Nutritions"));

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

        // MARK: Setup title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(applicationTitleLabel, BorderLayout.CENTER);

        // MARK: Setup menu item selection panel
        chooseMenuItemsPanel = new JPanel();
        chooseMenuItemsPanel.setLayout(new BoxLayout(chooseMenuItemsPanel, BoxLayout.X_AXIS));

        chooseMenuItemsPanel.add(foodLabel);
        chooseMenuItemsPanel.add(foodComboBox);
        chooseMenuItemsPanel.add(beverageLabel);
        chooseMenuItemsPanel.add(beverageComboBox);
        chooseMenuItemsPanel.setBorder(BorderFactory.createTitledBorder("Choose Menu Items"));
        

        // MARK: Setup command button area
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 7, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Command Buttons"));
        buttonPanel.add(enterDataButton);
        buttonPanel.add(displayChoicesButton);
        buttonPanel.add(displayOrderButton);
        buttonPanel.add(clearDisplayButton);
        buttonPanel.add(submitOrderButton);
        buttonPanel.add(quitButton);

        // MARK: Attach the panels to the root container and add this container to the JFrame
        rootPanel.add(titlePanel);
        rootPanel.add(customerDetailsPanel);
        rootPanel.add(chooseMenuItemsPanel);
        rootPanel.add(orderTableScrollPane);
        rootPanel.add(buttonPanel);

        add(rootPanel);
    }
    
    // MARK: Methods to add listeners to the different UI elements
    
    /**
     * A method to execute listener for menu button
     * 
     * @param menuAboutListener containing a listener of exit button
     */
    public void addMenuAboutListener(ActionListener menuAboutListener) {
        menuAbout.addActionListener(menuAboutListener);
    }
    
    /**
     * A method to execute listener for breakfast radio button
     * 
     * @param breakfastRadioButtonListener containing a listener of enter breakfast radio button
     */
    public void addBreakfastRadioButtonListener(ActionListener breakfastRadioButtonListener) {
        breakfastRadioButton.addActionListener(breakfastRadioButtonListener);
    }

    /**
     * A method to execute listener for lunch radio button
     * 
     * @param lunchRadioButtonListener containing a listener of enter lunch radio button
     */
    public void addLunchRadioButtonListener(ActionListener lunchRadioButtonListener) {
        lunchRadioButton.addActionListener(lunchRadioButtonListener);
    }

    /**
     * A method to execute listener for dinner radio button
     * 
     * @param dinnerRadioButtonListener containing a listener of enter dinner radio button
     */
    public void addDinnerRadioButtonListener(ActionListener dinnerRadioButtonListener) {
        dinnerRadioButton.addActionListener(dinnerRadioButtonListener);
    }
    
    /**
     * A method to execute listener for enter data button
     * 
     * @param enterDataButtonListener containing a listener of enter data button
     */
    public void addEnterDataButtonListener(ActionListener enterDataButtonListener) {
        enterDataButton.addActionListener(enterDataButtonListener);
    }

    /**
     * A method to execute listener for display choices button
     * 
     * @param displayChoicesButtonListener containing a listener of display choices button
     */
    public void addDisplayChoicesButtonListener(ActionListener displayChoicesButtonListener) {
        displayChoicesButton.addActionListener(displayChoicesButtonListener);
    }

    /**
     * A method to execute listener for display order button
     * 
     * @param displayOrderButtonListener containing a listener of display order button
     */
    public void addDisplayOrderButtonListener(ActionListener displayOrderButtonListener) {
        displayOrderButton.addActionListener(displayOrderButtonListener);
    }

    /**
     * A method to execute listener for clear display button
     * 
     * @param clearDisplayButtonListener containing a listener of clear display button
     */
    public void addClearDisplayButtonListener(ActionListener clearDisplayButtonListener) {
        clearDisplayButton.addActionListener(clearDisplayButtonListener);
    }
    
    /**
     * A method to execute listener for submit order button
     * 
     * @param quitButtonListener containing a listener of submit order button
     */
    public void addSubmitOrderButtonListener(ActionListener submitOrderButtonListener) {
        submitOrderButton.addActionListener(submitOrderButtonListener);
    }
    
    /**
     * A method to execute listener for quit button
     * 
     * @param quitButtonListener containing a listener of enter quit button
     */
    public void addQuitButtonListener(ActionListener quitButtonListener) {
        quitButton.addActionListener(quitButtonListener);
    }

    /**
     * A method to execute an message on the screen
     * 
     * @param information containing information to show the message to the user
     * @param titleDialog containing a title for the message dialog
     */
    public void showMessageDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.INFORMATION_MESSAGE);
    }

    
    /**
     * Create array containing possible table numbers
     * @return
     */
    public String[] getTableNumbers() {
        int maxTableNumbers = 12;
        String[] tableNumbers = new String[maxTableNumbers + 1];
        tableNumbers[0] = "Select a table number";
        for(int i = 1; i <= 12; i++) {
            tableNumbers[i] = Integer.toString(i);
        }
        return tableNumbers;
    }

    /**
     * Get the customer name input area text.
     * @return Customer name input area text.
     */
    public String getCustomerName() {
        return nameTextArea.getText();
    }

    /**
     * Get the customer table selected jcombobox item in string format.
     * @return string representing the currently selected item
     */
    public String getCustomerTable() {
        return tableNumberComboBox.getSelectedItem().toString();
    }

    /**
     * Get the food combo box.
     * @return The food combobox
     */
    public JComboBox<String> getFoodComboBox() {
        return foodComboBox;
    }

    /**
     * Get the beverage combo box
     * @return The beverage combo box
     */
    public JComboBox<String> getBeverageComboBox() {
        return beverageComboBox;
    }

    /**
     * Get the breakfast radio button.
     * @return The breakfast radio button
     */
    public JRadioButton getBreakfastRadioButton() {
        return breakfastRadioButton;
    }

    /**
     * Get the submit order button.
     * @return The submit order button.
     */
    public JButton getSubmitOrderButton() {
        return submitOrderButton;
    }

    /**
     * Displays an error dialog with the given string and title
     * @param information information to display in dialog
     * @param titleDialog information to display in title bar.
     */
    public void showErrorDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Get the table number combox
     * @return the table number combox
     */
    public JComboBox getTableNumberComboBox() {
        return tableNumberComboBox;
    }

    /**
     * Get the name text area
     * @return The name text area
     */
    public JTextArea getNameTextArea() {
        return nameTextArea;
    }

    /**
     * Get the clear display button
     * @return The clear display button
     */
    public JButton getClearDisplayButton() {
        return clearDisplayButton;
    }

    /**
     * Get the order item details table
     * @return The order item details table.
     */
    public JTable getOrderItemTable() {
        return orderItemTable;
    }
}
