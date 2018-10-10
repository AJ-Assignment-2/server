/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import clients.customer.MenuItemTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import model.Order.Order;
import static model.Order.OrderState.SERVED;

/**
 *
 * @author Imanuel
 */
public class ReceptionView extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;

    private Border border;

    private JPanel northPanel;

    private JPanel centerPanel;
    private JPanel servingOrdersPanel;
    private JPanel billingOrdersPanel;
    private JPanel servedBilledPanel;
    private JPanel orderStatusPanel;
    private JLabel servingOrders;
    private JLabel billingOrders;
    private JLabel orderHistory;
//    private JTextArea servingOrdersTextArea;    ////////////////////////////////////////////////
//    private JTextArea billingOrdersTextArea;    ////////////////////////////////////////////////
//    private JTextArea orderHistoryTextArea;     ////////////////////////////////////////////////
    private JScrollPane scrollableServingOrders;
    private JScrollPane scrollableBillingOrders;
    private JScrollPane scrollableOrderHistory;

    private JPanel southPanel;
    private JButton billButton;
    private JButton exitButton;

    private String[][] labels = {{"Customer Details"}, {"Order Status"}, {"Command Buttons"}};

    public ReceptionView() {
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        this.menuAbout = new JMenuItem("About");
        menuFile.add(menuAbout);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);

        border = BorderFactory.createLineBorder(Color.BLACK);

        northPanel = new JPanel();

//        northPanel.add();
        centerPanel = new JPanel();
        servingOrdersPanel = new JPanel();
        billingOrdersPanel = new JPanel();
        servedBilledPanel = new JPanel(new BorderLayout());
        servingOrdersPanel.setLayout(new BorderLayout());
        billingOrdersPanel.setLayout(new BorderLayout());
        orderStatusPanel = new JPanel(new BorderLayout());
        orderStatusPanel.setLayout(new GridLayout(1, 2, 50, 10));
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder(labels[1][0]));
        servingOrders = new JLabel("Orders with served state (No orders available to serve)");
        billingOrders = new JLabel("Orders with billing state (No orders available to bill)");
        orderHistory = new JLabel("All Orders have been processed");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        billingOrders.setHorizontalAlignment(JLabel.CENTER);
        orderHistory.setHorizontalAlignment(JLabel.CENTER);
        scrollableServingOrders = new JScrollPane(setupDefaultTable());
        scrollableBillingOrders = new JScrollPane(setupDefaultTable());
        scrollableOrderHistory = new JScrollPane(setupDefaultTable());
        scrollableServingOrders.setPreferredSize(new Dimension(0, 200));
        scrollableBillingOrders.setPreferredSize(new Dimension(0, 200));
        scrollableOrderHistory.setPreferredSize(new Dimension(700, 200));
        scrollableServingOrders.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableBillingOrders.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableOrderHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        servingOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        billingOrdersPanel.add(billingOrders, BorderLayout.NORTH);
        servingOrdersPanel.add(scrollableServingOrders, BorderLayout.SOUTH);
        billingOrdersPanel.add(scrollableBillingOrders, BorderLayout.SOUTH);
        orderStatusPanel.add(servingOrdersPanel);
        orderStatusPanel.add(billingOrdersPanel);
        servedBilledPanel.add(orderHistory, BorderLayout.NORTH);
        servedBilledPanel.add(scrollableOrderHistory, BorderLayout.SOUTH);
        centerPanel.add(orderStatusPanel, BorderLayout.NORTH);
        centerPanel.add(servedBilledPanel, BorderLayout.SOUTH);

        southPanel = new JPanel();
        billButton = new JButton("Bill");
        exitButton = new JButton("Exit");
        southPanel.add(billButton);
        southPanel.add(exitButton);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void addMenuAboutListener(ActionListener menuAboutListener) {
        menuAbout.addActionListener(menuAboutListener);
    }

    public void addBillButtonListener(ActionListener billButtonListener) {
        billButton.addActionListener(billButtonListener);
    }

    public void addExitButtonListener(ActionListener exitButtonListener) {
        exitButton.addActionListener(exitButtonListener);
    }

    public void setupServedTable() {
        
    }

    public void setupBilledTable() {

    }

    public void setupItemsTable() {
        
    }

    public void showMessageDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.ERROR_MESSAGE);
    }

    public JTable setupDefaultTable() {
        JTable defaultTable = new JTable();
        return defaultTable;
    }
//    public String getTableNumber(){
//        return String.valueOf(selectedTableNumber.getSelectedItem());
//    }
}
