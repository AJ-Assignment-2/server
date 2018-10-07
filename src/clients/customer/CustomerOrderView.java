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
    private CustomerOrderModel customerOrderModel;
    
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
    
    
    private JPanel southPanel;
    private JPanel customerOrderPanel;
    private JTable customerOrderTable;
    private Object rowCustomerOrderTable[];
    private DefaultTableModel customerOrdermodel;
    private JScrollPane customerOrderJScrollPane;
    
    private JPanel buttonPanel;
    private JButton enterDataButton;
    private JButton displayChoicesButton;
    private JButton displayOrderButton;
    private JButton clearDisplayButton;
    private JButton quitButton;
    
    private String[][] labels={{"Customer Details"}, {"Choose Menu Items"}, {"Menu Choices and Nutrition Information"}, {"Customer Order"}, {"Command Buttons"}};
    
    public CustomerOrderView(){
        this.customerOrderModel =new CustomerOrderModel();
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

        
        northPanel.setLayout(new BorderLayout());
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
        
        northPanel.add(menuTitleLabel, BorderLayout.NORTH);
        northPanel.add(customerDetailsPanel, BorderLayout.CENTER);
        northPanel.add(chooseMenuItemsPanel, BorderLayout.SOUTH);
        

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
        nameLabel.setText("Unknown");/////////////////////////////////////////////////////////////////////////////get from server
        tableNumberLabel.setText("00000000");/////////////////////////////////////////////////////////////////////get from server
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
    
    public void displayDetailsChoices(){
        clearDisplayButton.setEnabled(true);
        orderTable=new JTable();
        orderTable.setEnabled(false);
        tablePanel=new JPanel();
        centrePanel=new JPanel();
        
        String selectedFood=foodComboBox.getItemAt(foodComboBox.getSelectedIndex());
        String selectedBeverage=beverageComboBox.getItemAt(beverageComboBox.getSelectedIndex());
        orderTable.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Item Name", "Energy", "Protein", "Carbohydrate", "Total Fat", "Fibre", "Price"}));
        row=new Object[7];
        model=(DefaultTableModel) orderTable.getModel();
        if(foodComboBox.getSelectedIndex()!=0 && beverageComboBox.getSelectedIndex()!=0){
            for(MenuItem menu: customerOrderModel.getMenuWithCategory(FOOD)){
                if(menu.getName().equals(selectedFood)){
                    row[0]=menu.getName();
                    row[1]=menu.getEnergy();
                    row[2]=menu.getProtean();
                    row[3]=menu.getCarbohydrates();
                    row[4]=menu.getFat();
                    row[5]=menu.getFibre();
                    row[6]=menu.getPrice();
                    model.addRow(row);
                }
            }
            for(MenuItem menu: customerOrderModel.getMenuWithCategory(BEVERAGE)){
                if(menu.getName().equals(selectedBeverage)){
                    row[0]=menu.getName();
                    row[1]=menu.getEnergy();
                    row[2]=menu.getProtean();
                    row[3]=menu.getCarbohydrates();
                    row[4]=menu.getFat();
                    row[5]=menu.getFibre();
                    row[6]=menu.getPrice();
                    model.addRow(row);
                }
            }
        }else if(foodComboBox.getSelectedIndex()!=0){
            for(MenuItem menu: customerOrderModel.getMenuWithCategory(FOOD)){
                if(menu.getName().equals(selectedFood)){
                    row[0]=menu.getName();
                    row[1]=menu.getEnergy();
                    row[2]=menu.getProtean();
                    row[3]=menu.getCarbohydrates();
                    row[4]=menu.getFat();
                    row[5]=menu.getFibre();
                    row[6]=menu.getPrice();
                    model.addRow(row);
                }
            }
        }else if(beverageComboBox.getSelectedIndex()!=0){
            for(MenuItem menu: customerOrderModel.getMenuWithCategory(BEVERAGE)){
                if(menu.getName().equals(selectedBeverage)){
                    row[0]=menu.getName();
                    row[1]=menu.getEnergy();
                    row[2]=menu.getProtean();
                    row[3]=menu.getCarbohydrates();
                    row[4]=menu.getFat();
                    row[5]=menu.getFibre();
                    row[6]=menu.getPrice();
                    model.addRow(row);
                }
            }
        }
        jScrollPane=new JScrollPane(orderTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setPreferredSize(new Dimension(850, 100));
        tablePanel.add(jScrollPane);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Menu Choices and Nutrition Information"));
        this.getContentPane().remove(centrePanel);
        centrePanel.add(tablePanel);
        this.add(centrePanel, BorderLayout.CENTER);
        this.getContentPane().invalidate();
        this.getContentPane().validate();
    }
    
    public void showMessageDialog(String information, String titleDialog){
        JOptionPane.showMessageDialog(this,information,titleDialog,JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showErrorDialog(String information, String titleDialog){
        JOptionPane.showMessageDialog(this,information,titleDialog,JOptionPane.ERROR_MESSAGE);
    }
    
    public void showAllOrders(ArrayList<String> allOrders){
        //create table
        customerOrderPanel=new JPanel();
        customerOrderTable=new JTable();
        customerOrderTable.setEnabled(false);
        
        customerOrderTable.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Item Name", "Energy", "Protein", "Carbohydrate", "Total Fat", "Fibre", "Price"}));
        rowCustomerOrderTable=new Object[7];
        customerOrdermodel=(DefaultTableModel) customerOrderTable.getModel();
        for(String order: allOrders){
            String[] orderDesc = order.split(",");
            for(int i=0;i<7;i++){
                rowCustomerOrderTable[i]=orderDesc[i];
            }
            customerOrdermodel.addRow(rowCustomerOrderTable);
        }
        customerOrderJScrollPane=new JScrollPane(customerOrderTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        customerOrderJScrollPane.setPreferredSize(new Dimension(850, 100));
        customerOrderPanel.add(customerOrderJScrollPane);
        customerOrderPanel.setBorder(BorderFactory.createTitledBorder(labels[3][0]));
        southPanel.add(customerOrderPanel, BorderLayout.NORTH);
        southPanel.invalidate();
        southPanel.validate();
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
}
