package clients.customer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;

import static model.MenuItem.MenuItemCategory.BEVERAGE;
import static model.MenuItem.MenuItemCategory.FOOD;

/**
 *
 * @author Imanuel
 */
public class CustomerOrderView extends JFrame{
    private Border border;
    private JPanel northPanel;
    private JLabel menuTitleLabel;
    private JPanel customerDetailsPanel;
    private JPanel customerNamePanel;
    private JPanel customerTablePanel;
    private JPanel mealPanel;
    private JLabel customerNameLabel;
    private JLabel nameLabel;
    private JLabel customerTableNumberLabel;
    private JLabel tableNumberLabel;
    private JLabel mealType;
    private JRadioButton breakfastRadioButton;
    private JRadioButton lunchRadioButton;
    private JRadioButton dinnerRadioButton;
    private ButtonGroup radioButtonGroup;

    private JPanel chooseMenuItemsPanel;
    private JLabel foodLabel;
    private JComboBox<String> foodComboBox;
    private JLabel beverageLabel;
    private JComboBox<String> beverageComboBox;
    
    
    private JPanel centrePanel;
    private Object row[];
    private JPanel tablePanel;
    private JTable orderTable;
    private DefaultTableModel model;
    private JScrollPane jScrollPane;

    private JScrollPane orderTableScrollPane;
    
    
    private JPanel southPanel;
    
    private JPanel buttonPanel;
    private JButton enterDataButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton clearDisplayButton;
    private JButton quitButton;
    
    private String[][] labels={{"Customer Details"}, {"Choose Menu Items"}, {"Menu Choices and Nutrition Information"}, {"Customer Order"}, {"Command Buttons"}};
    
    public CustomerOrderView(){
        border=BorderFactory.createLineBorder(Color.BLACK);
        
        northPanel=new JPanel();
        menuTitleLabel=new JLabel();
        menuTitleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        menuTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuTitleLabel.setText("Welcome to Restaurant");
        customerDetailsPanel=new JPanel();
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder(labels[0][0]));
        customerNamePanel=new JPanel();
        customerTablePanel=new JPanel();
        mealPanel=new JPanel();
        customerNameLabel=new JLabel("Customer Name: ");
        nameLabel=new JLabel();
        customerTableNumberLabel=new JLabel("Table Number: ");
        tableNumberLabel=new JLabel();
        setupCustomerNameAndTableNumber();
        mealType=new JLabel("Meal Type: ");
        breakfastRadioButton=new JRadioButton("Breakfast");
        lunchRadioButton=new JRadioButton("Lunch");
        dinnerRadioButton=new JRadioButton("Dinner");
        radioButtonGroup=new ButtonGroup();
        
        chooseMenuItemsPanel=new JPanel();       
        chooseMenuItemsPanel.setBorder(BorderFactory.createTitledBorder(labels[1][0]));
        foodLabel=new JLabel("Food");
        foodComboBox=new JComboBox<>();
        foodComboBox.addItem("-------- Select the food --------");
        beverageLabel=new JLabel("Beverage");
        beverageComboBox = new JComboBox<>();
        beverageComboBox.addItem("-------- Select the beverage --------");

        
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        customerDetailsPanel.setLayout(new GridLayout(1,3));
        
        customerNamePanel.add(customerNameLabel);
        customerNamePanel.add(nameLabel);
        customerTablePanel.add(customerTableNumberLabel);
        customerTablePanel.add(tableNumberLabel);
        radioButtonGroup.add(breakfastRadioButton);
        radioButtonGroup.add(lunchRadioButton);
        radioButtonGroup.add(dinnerRadioButton);        
        mealPanel.add(mealType);
        mealPanel.add(breakfastRadioButton);
        mealPanel.add(lunchRadioButton);
        mealPanel.add(dinnerRadioButton);
        customerDetailsPanel.add(customerNamePanel);
        customerDetailsPanel.add(customerTablePanel);
        customerDetailsPanel.add(mealPanel);

        
        chooseMenuItemsPanel.add(foodLabel);
        chooseMenuItemsPanel.add(foodComboBox);
        chooseMenuItemsPanel.add(beverageLabel);
        chooseMenuItemsPanel.add(beverageComboBox);
        
        northPanel.add(menuTitleLabel);
        northPanel.add(customerDetailsPanel);
        northPanel.add(chooseMenuItemsPanel);

        southPanel=new JPanel();
        southPanel.setLayout(new BorderLayout());
        buttonPanel=new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder(labels[4][0]));
        enterDataButton=new JButton("Enter Data");
        displayChoicesButton=new JButton("Display Choices");
        displayOrderButton=new JButton("Display Order");
        clearDisplayButton=new JButton("Clear Display");
        clearDisplayButton.setEnabled(false);
        quitButton=new JButton("Exit");
        
        buttonPanel.setLayout(new GridLayout(1,7, 10, 10));
        buttonPanel.add(enterDataButton);
        buttonPanel.add(displayChoicesButton);
        buttonPanel.add(displayOrderButton);
        buttonPanel.add(clearDisplayButton);
        buttonPanel.add(quitButton);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    public void setupCustomerNameAndTableNumber(){
        nameLabel.setText("Unknown"); // Get from server
        tableNumberLabel.setText("00000000"); // Get from server
    }
    
    public void addBreakfastRadioButtonListener(ActionListener breakfastRadioButtonListener){
        breakfastRadioButton.addActionListener(breakfastRadioButtonListener);
    }
    
    public void addLunchRadioButtonListener(ActionListener lunchRadioButtonListener){
        lunchRadioButton.addActionListener(lunchRadioButtonListener);
    }
    
    public void addDinnerRadioButtonListener(ActionListener dinnerRadioButtonListener){
        dinnerRadioButton.addActionListener(dinnerRadioButtonListener);
    }
    
    public void addEnterDataButtonListener(ActionListener enterDataButtonListener){
        enterDataButton.addActionListener(enterDataButtonListener);
    }
    
    public void addDisplayChoicesButtonListener(ActionListener displayChoicesButtonListener){
        displayChoicesButton.addActionListener(displayChoicesButtonListener);
    }
    
    public void addDisplayOrderButtonListener(ActionListener displayOrderButtonListener){
        displayOrderButton.addActionListener(displayOrderButtonListener);
    }
    
    public void addClearDisplayButtonListener(ActionListener clearDisplayButtonListener){
        clearDisplayButton.addActionListener(clearDisplayButtonListener);
    }
    
    public void addQuitButtonListener(ActionListener quitButtonListener){
        quitButton.addActionListener(quitButtonListener);
    }
    
    public String getCustomerName(){
        return nameLabel.getText();
    }
    
    public String getCustomerTable(){
        return tableNumberLabel.getText();
    }

    public JComboBox<String> getFoodComboBox() {
        return foodComboBox;
    }

    public JComboBox<String> getBeverageComboBox() {
        return beverageComboBox;
    }

    public String getChosenFood(){
        return foodComboBox.getSelectedItem().toString();
    }
    
    public String getChosenBeverage(){
        return beverageComboBox.getSelectedItem().toString();
    }
    
    public void showErrorDialog(String information, String titleDialog){
        JOptionPane.showMessageDialog(this,information,titleDialog,JOptionPane.ERROR_MESSAGE);
    }
    
    public void setResetScreen(){
        radioButtonGroup.clearSelection();
        foodComboBox.removeAllItems();
        beverageComboBox.removeAllItems();
        foodComboBox.addItem("-------- Select the food --------");
        beverageComboBox.addItem("-------- Select the beverage --------");
        this.getContentPane().remove(centrePanel);
        this.getContentPane().invalidate();
        this.getContentPane().validate();
        clearDisplayButton.setEnabled(false);
    }

    public void displayOrderTable(JTable table) {
        if (orderTableScrollPane != null) this.northPanel.remove(orderTableScrollPane);
        revalidate();
        repaint();
        orderTableScrollPane = new JScrollPane(table);
        orderTableScrollPane.setPreferredSize(new Dimension(0, 200));
        table.getColumnModel().getColumn(0).setPreferredWidth(350);
        table.getColumnModel().getColumn(4).setPreferredWidth(125);
        this.northPanel.add(orderTableScrollPane);
        revalidate();
        repaint();
    }
}
