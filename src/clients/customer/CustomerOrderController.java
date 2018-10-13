package clients.customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.MenuItem.MenuItem;
import static model.MenuItem.MenuItemCategory.BEVERAGE;
import static model.MenuItem.MenuItemCategory.FOOD;

import model.MenuItem.MenuItemTableModel;
import model.MenuItem.MenuItemType;
import model.Order.OrderState;

import javax.swing.*;

public class CustomerOrderController {

    private CustomerOrderView customerOrderView;
    private CustomerOrderModel customerOrderModel;

    public CustomerOrderController(CustomerOrderView customerOrderView, CustomerOrderModel customerOrderModel) {
        this.customerOrderModel = customerOrderModel;
        this.customerOrderView = customerOrderView;

        this.customerOrderView.addBreakfastRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addLunchRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addDinnerRadioButtonListener(new CategorySelectedButtonListener());
        this.customerOrderView.addEnterDataButtonListener(new EnterDataButtonListener());
        this.customerOrderView.addDisplayChoicesButtonListener(new DisplayChoicesButtonListener());
        this.customerOrderView.addDisplayOrderButtonListener(new DisplayOrderButtonListener());
        this.customerOrderView.addSubmitOrderButtonListener(new SubmitOrderButtonListener());
        this.customerOrderView.addClearDisplayButtonListener(new ClearButtonListener());
        this.customerOrderView.addQuitButtonListener(new QuitSystem());
    }

    class CategorySelectedButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItemType selectedType = null;
            for (MenuItemType type : MenuItemType.values()) {
                if (type.toString().equalsIgnoreCase(((JRadioButton) e.getSource()).getText())) {
                    selectedType = type;
                }
            }

            List<MenuItem> food = customerOrderModel.getMenuWithTypeCategory(FOOD, selectedType);
            List<MenuItem> beverage = customerOrderModel.getMenuWithTypeCategory(BEVERAGE, selectedType);

            DefaultComboBoxModel foodComboBoxModel = new DefaultComboBoxModel(food.toArray());
            DefaultComboBoxModel beverageComboBoxModel = new DefaultComboBoxModel(beverage.toArray());

            customerOrderView.getFoodComboBox().setModel(foodComboBoxModel);
            customerOrderView.getFoodComboBox().setRenderer(new MenuItemNameCellRenderer());

            customerOrderView.getBeverageComboBox().setModel(beverageComboBoxModel);
            customerOrderView.getBeverageComboBox().setRenderer(new MenuItemNameCellRenderer());
        }

    }

    class EnterDataButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!customerOrderView.getCustomerName().equals("") || !customerOrderView.getCustomerTable().equals("Select Table Number")) {
                try {
                    customerOrderView.setDisableInputNameAndTable();
                    customerOrderModel.addOrderItem((MenuItem) customerOrderView.getFoodComboBox().getSelectedItem());
                    customerOrderModel.addOrderItem((MenuItem) customerOrderView.getBeverageComboBox().getSelectedItem());

                    // Initialise the order if this is the first item being added.
                    if (customerOrderModel.getCustomerOrder().getCustomerName() == null) {
                        customerOrderModel.getCustomerOrder().setState(OrderState.WAITING);

                        int selectedTableNumberIndex = customerOrderView.getTableNumberComboBox().getSelectedIndex();
                        String tableNumber = (String)customerOrderView.getTableNumberComboBox().getItemAt(selectedTableNumberIndex);

                        customerOrderModel.getCustomerOrder().setTableNumber(Integer.parseInt(tableNumber));
                        customerOrderModel.getCustomerOrder().setCustomerName(customerOrderView.getCustomerName());
                    }

                } catch (ClassCastException exception) {
                    System.out.println(exception);
                    customerOrderView.showErrorDialog("Hi " + customerOrderView.getCustomerName() + "\nPlease select a valid food and beverage", "Select Menu");
                }
            } else {
                customerOrderView.showErrorDialog("Please enter your name or your table number", "Incorrect Name or Table Number");
            }
        }
    }

    class DisplayChoicesButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<MenuItem> focusedItems = new ArrayList<>();
                focusedItems.add((MenuItem) customerOrderView.getFoodComboBox().getSelectedItem());
                focusedItems.add((MenuItem) customerOrderView.getBeverageComboBox().getSelectedItem());

                JTable orderItemTable = customerOrderView.getOrderItemTable();
                MenuItemTableModel menuItemTableModel = (MenuItemTableModel)orderItemTable.getModel();
                menuItemTableModel.setMenuItems(focusedItems);
                menuItemTableModel.fireTableDataChanged();
            } catch (ClassCastException exception) {
                customerOrderView.showErrorDialog("Hi " + customerOrderView.getCustomerName() + "\nPlease select a valid food and beverage", "Select Menu");
            }
        }

    }

    class DisplayOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!customerOrderModel.getCustomerOrder().getMenuItemSelections().isEmpty()) {
                List<MenuItem> orderMenuItems = new ArrayList<>(customerOrderModel.getCustomerOrder().getMenuItemSelections().keySet());
                MenuItemTableModel menuItemTableModel = (MenuItemTableModel)customerOrderView.getOrderItemTable().getModel();

                menuItemTableModel.setMenuItems(orderMenuItems);
                menuItemTableModel.fireTableDataChanged();
            } else {
                customerOrderView.showErrorDialog("Hi " + customerOrderView.getCustomerName() + "\nPlease order at least one food or beverage", "Order Menu");
            }
        }
    }

    class SubmitOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customerOrderModel.submitCustomerOrder();
        }
        
    }

    class ClearButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            customerOrderView.setResetScreen();
        }

    }

        class QuitSystem implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        }

        private class MenuItemNameCellRenderer extends DefaultListCellRenderer {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof MenuItem) {
                    MenuItem item = (MenuItem) value;
                    setText(item.getName());
                }
                return this;
            }
        }
    }
