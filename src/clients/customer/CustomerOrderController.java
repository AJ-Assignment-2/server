package clients.customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.MenuItem.MenuItem;
import static model.MenuItem.MenuItemCategory.BEVERAGE;
import static model.MenuItem.MenuItemCategory.FOOD;
import model.MenuItem.MenuItemType;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Imanuel
 */
public class CustomerOrderController {
    private CustomerOrderView customerOrderView;
    private CustomerOrderModel customerOrderModel;
    
    public CustomerOrderController(CustomerOrderView customerOrderView, CustomerOrderModel customerOrderModel){
        this.customerOrderModel = customerOrderModel;
        this.customerOrderView = customerOrderView;
        
        this.customerOrderView.addBreakfastRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addLunchRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addDinnerRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addEnterDataButtonListener(new EnterDataButtonListener());
        this.customerOrderView.addDisplayChoicesButtonListener(new DisplayChoicesButtonListener());
        this.customerOrderView.addDisplayOrderButtonListener(new DisplayOrderButtonListener());
        this.customerOrderView.addClearDisplayButtonListener(new ClearButtonListener());
        this.customerOrderView.addQuitButtonListener(new QuitSystem());
    }

    class CategorySelectedButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItemType selectedType = null;
            for (MenuItemType type : MenuItemType.values()) {
                if (type.toString().equalsIgnoreCase(((JRadioButton)e.getSource()).getText())) {
                    selectedType = type;
                }
            }

            List<MenuItem> food = customerOrderModel.getMenuWithTypeCategory(FOOD, selectedType);
            List<MenuItem> beverage= customerOrderModel.getMenuWithTypeCategory(BEVERAGE, selectedType);

            DefaultComboBoxModel foodComboBoxModel = new DefaultComboBoxModel(food.toArray());
            DefaultComboBoxModel beverageComboBoxModel = new DefaultComboBoxModel(beverage.toArray());

            customerOrderView.getFoodComboBox().setModel(foodComboBoxModel);
            customerOrderView.getFoodComboBox().setRenderer(new MenuItemNameCellRenderer());

            customerOrderView.getBeverageComboBox().setModel(beverageComboBoxModel);
            customerOrderView.getBeverageComboBox().setRenderer(new MenuItemNameCellRenderer());
        }
        
    }
    
    class EnterDataButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                customerOrderModel.addOrderItem((MenuItem)customerOrderView.getFoodComboBox().getSelectedItem());
                customerOrderModel.addOrderItem((MenuItem)customerOrderView.getBeverageComboBox().getSelectedItem());
            } catch (ClassCastException exception) {
                customerOrderView.showErrorDialog("Hi "+ customerOrderView.getCustomerName()+"\nPlease select a valid food and beverage", "Select Menu");
            }
        }
    }
    
    class DisplayChoicesButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<MenuItem> focusedItems = new ArrayList<>();
                focusedItems.add((MenuItem)customerOrderView.getFoodComboBox().getSelectedItem());
                focusedItems.add((MenuItem)customerOrderView.getBeverageComboBox().getSelectedItem());

                MenuItemTableModel tableModel = new MenuItemTableModel(focusedItems);
                JTable orderTable = new JTable(tableModel);
                orderTable.setGridColor(Color.GRAY);
                customerOrderView.displayOrderTable(orderTable);
            } catch (ClassCastException exception) {
                customerOrderView.showErrorDialog("Hi "+ customerOrderView.getCustomerName()+"\nPlease select a valid food and beverage", "Select Menu");
            }
        }
        
    }
    
    class DisplayOrderButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!customerOrderModel.getCustomerOrder().getMenuItemSelections().isEmpty()){

                MenuItemTableModel tableModel = new MenuItemTableModel(
                        new ArrayList<>(customerOrderModel.getCustomerOrder().getMenuItemSelections().keySet()));
                JTable orderTable = new JTable(tableModel);
                orderTable.setGridColor(Color.GRAY);
                customerOrderView.displayOrderTable(orderTable);
            } else customerOrderView.showErrorDialog("Hi "+ customerOrderView.getCustomerName()+"\nPlease order at least one food or beverage", "Order Menu");
        }
    }
    
    class ClearButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            customerOrderView.setResetScreen();
        }
        
    }
    
    class QuitSystem implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }

    private class MenuItemNameCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof MenuItem){
                MenuItem item = (MenuItem) value;
                setText(item.getName());
            }
            return this;
        }
    }
}
