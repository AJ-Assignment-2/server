package clients.customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.ColumnWidthUtil;
import model.MenuItem.*;

import static model.MenuItem.MenuItemCategory.BEVERAGE;
import static model.MenuItem.MenuItemCategory.FOOD;

import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderState;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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

        this.customerOrderView.getBreakfastRadioButton().doClick();
    }

    private void clearOrder() {
        customerOrderModel.setCustomerOrder(new Order());
        MenuItemTotalsTableModel menuItemTotalsTableModel = (MenuItemTotalsTableModel) customerOrderView.getOrderItemTable().getModel();
        menuItemTotalsTableModel.setMenuItems(new ArrayList<>());
        menuItemTotalsTableModel.fireTableDataChanged();

        customerOrderView.getNameTextArea().setText("");
        customerOrderView.getNameTextArea().setEnabled(true);
        customerOrderView.getNameTextArea().setBackground(Color.WHITE);
        customerOrderView.getNameTextArea().setForeground(Color.BLACK);

        customerOrderView.getTableNumberComboBox().setEnabled(true);
        customerOrderView.getSubmitOrderButton().setEnabled(false);
        customerOrderView.getClearDisplayButton().setEnabled(false);
        customerOrderView.getTableNumberComboBox().setSelectedIndex(0);
    }

    private class CategorySelectedButtonListener implements ActionListener {

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

    private class EnterDataButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!customerOrderView.getCustomerName().equals("") && !customerOrderView.getCustomerTable().equals("Select a table number")) {
                customerOrderView.getClearDisplayButton().setEnabled(true);
                customerOrderView.getSubmitOrderButton().setEnabled(true);

                // Disable the name and table  number inputs
                if (customerOrderView.getNameTextArea().isEnabled() || customerOrderView.getTableNumberComboBox().isEnabled()) {
                    customerOrderView.getTableNumberComboBox().setEnabled(false);
                    customerOrderView.getNameTextArea().setEnabled(false);
                    customerOrderView.getNameTextArea().setBackground(customerOrderView.getTableNumberComboBox().getBackground());
                    customerOrderView.getNameTextArea().setForeground(customerOrderView.getTableNumberComboBox().getForeground());
                }

                customerOrderModel.addOrderItem((MenuItem) customerOrderView.getFoodComboBox().getSelectedItem());
                customerOrderModel.addOrderItem((MenuItem) customerOrderView.getBeverageComboBox().getSelectedItem());

                // Initialise the order if this is the first item being added.
                if (customerOrderModel.getCustomerOrder().getCustomerName() == null) {
                    customerOrderModel.getCustomerOrder().setState(OrderState.WAITING);

                    int selectedTableNumberIndex = customerOrderView.getTableNumberComboBox().getSelectedIndex();
                    String tableNumber = (String) customerOrderView.getTableNumberComboBox().getItemAt(selectedTableNumberIndex);

                    customerOrderModel.getCustomerOrder().setTableNumber(Integer.parseInt(tableNumber));
                    customerOrderModel.getCustomerOrder().setCustomerName(customerOrderView.getCustomerName());
                }
            } else {
                customerOrderView.showErrorDialog("Please enter your name or your table number", "Incorrect Name or Table Number");
            }
        }
    }

    private class DisplayChoicesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> focusedItems = new ArrayList<>();
            focusedItems.add((MenuItem) customerOrderView.getFoodComboBox().getSelectedItem());
            focusedItems.add((MenuItem) customerOrderView.getBeverageComboBox().getSelectedItem());

            JTable orderItemTable = customerOrderView.getOrderItemTable();
            MenuItemTotalsTableModel menuItemTableModel = (MenuItemTotalsTableModel) orderItemTable.getModel();
            menuItemTableModel.setMenuItems(focusedItems);
            menuItemTableModel.fireTableDataChanged();
        }
    }

    private class DisplayOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> orderMenuItems = new ArrayList<>();

            if (!customerOrderModel.getCustomerOrder().getMenuItemSelections().isEmpty()) {
                for (MenuItem item : customerOrderModel.getCustomerOrder().getMenuItemSelections().keySet()) {
                    int quantity = customerOrderModel.getCustomerOrder().getMenuItemSelections().get(item);

                    for (int i = 0; i < quantity; i++) {
                        orderMenuItems.add(item);
                    }
                }

                MenuItemTotalsTableModel menuItemTableModel = (MenuItemTotalsTableModel) customerOrderView.getOrderItemTable().getModel();
                JTable table = customerOrderView.getOrderItemTable();

                menuItemTableModel.setMenuItems(orderMenuItems);
                menuItemTableModel.fireTableDataChanged();

                //customerOrderView.getOrderItemTable().setDefaultRenderer(String.class, new MenuItemColumnWidthRenderer());
                ColumnWidthUtil.adjustColumnWidths(table, new int[]{0,1,2,3,4,5,6,7,8});
            } else {
                customerOrderView.showErrorDialog("You have no items in your order!", "Please select items for your order");
            }
        }
    }

    private class SubmitOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (customerOrderModel.getCustomerOrder().getMenuItemSelections().size() > 0) {
                customerOrderModel.submitCustomerOrder();
                clearOrder();

            } else {
                customerOrderView.showErrorDialog("You have no items in your order!", "Please select items for your order");
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearOrder();
        }

    }

    private class QuitSystem implements ActionListener {
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
