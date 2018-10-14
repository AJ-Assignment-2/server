/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import model.MenuItem.MenuItemTotalsTableModel;
import model.Order.OrderTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class ReceptionView extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;

    private JPanel centerPanel;
    private JPanel servedOrdersPanel;
    private JPanel billedOrdersPanel;
    private JPanel orderItemDetailsPanel;
    private JPanel orderStatusPanel;

    private JLabel servingOrders;
    private JLabel billingOrders;
    private JLabel orderHistory;

    private JTable servedOrdersTable;
    private JTable billedOrdersTable;
    private JTable orderItemDetailTable;

    private JScrollPane servedOrdersTableContainer;
    private JScrollPane billedOrdersTableContainer;
    private JScrollPane orderItemDetailsContainer;

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

        // Initialise JTables
        servedOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));
        billedOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));
        orderItemDetailTable = new JTable(new MenuItemTotalsTableModel(new ArrayList<>()));

        centerPanel = new JPanel();
        servedOrdersPanel = new JPanel();
        billedOrdersPanel = new JPanel();
        orderItemDetailsPanel = new JPanel(new BorderLayout());
        servedOrdersPanel.setLayout(new BorderLayout());
        billedOrdersPanel.setLayout(new BorderLayout());
        orderStatusPanel = new JPanel(new BorderLayout());
        orderStatusPanel.setLayout(new GridLayout(1, 2, 50, 10));
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder(labels[1][0]));
        servingOrders = new JLabel("Orders waiting to be billed");
        billingOrders = new JLabel("Billed orders");
        orderHistory = new JLabel("Selected order details");
        servingOrders.setHorizontalAlignment(JLabel.CENTER);
        billingOrders.setHorizontalAlignment(JLabel.CENTER);
        orderHistory.setHorizontalAlignment(JLabel.CENTER);

        servedOrdersTableContainer = new JScrollPane(servedOrdersTable);
        billedOrdersTableContainer = new JScrollPane(billedOrdersTable);
        orderItemDetailsContainer = new JScrollPane(orderItemDetailTable);


        servedOrdersTableContainer.setPreferredSize(new Dimension(400, 200));
        billedOrdersTableContainer.setPreferredSize(new Dimension(400, 200));
        orderItemDetailsContainer.setPreferredSize(new Dimension(1050, 200));

        servedOrdersPanel.add(servingOrders, BorderLayout.NORTH);
        billedOrdersPanel.add(billingOrders, BorderLayout.NORTH);
        servedOrdersPanel.add(servedOrdersTableContainer, BorderLayout.SOUTH);
        billedOrdersPanel.add(billedOrdersTableContainer, BorderLayout.SOUTH);
        orderStatusPanel.add(servedOrdersPanel);
        orderStatusPanel.add(billedOrdersPanel);

        orderItemDetailsPanel.add(orderHistory, BorderLayout.NORTH);
        orderItemDetailsPanel.add(orderItemDetailsContainer, BorderLayout.SOUTH);

        centerPanel.add(orderStatusPanel, BorderLayout.NORTH);
        centerPanel.add(orderItemDetailsContainer, BorderLayout.SOUTH);

        southPanel = new JPanel();
        billButton = new JButton("Bill");
        billButton.setEnabled(false);
        exitButton = new JButton("Exit");
        southPanel.add(billButton);
        southPanel.add(exitButton);

        billedOrdersTable.getSelectionModel().addListSelectionListener(event -> servedOrdersTable.clearSelection());
        billedOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        billedOrdersTable.setCellSelectionEnabled(false);
        billedOrdersTable.setRowSelectionAllowed(true);
        servedOrdersTable.getSelectionModel().addListSelectionListener(event -> billedOrdersTable.clearSelection());
        servedOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Always highlight the entire row
        servedOrdersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = servedOrdersTable.rowAtPoint(e.getPoint());
                servedOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                billButton.setEnabled(true);
            }
        });

        // Always highlight the entire row
        billedOrdersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = billedOrdersTable.rowAtPoint(e.getPoint());
                billedOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                billButton.setEnabled(false);
            }
        });

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

    public void showMessageDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorDialog(String information, String titleDialog) {
        JOptionPane.showMessageDialog(this, information, titleDialog, JOptionPane.ERROR_MESSAGE);
    }

    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    public JTable getBilledOrdersTable() {
        return billedOrdersTable;
    }

    public JTable getOrderItemDetailTable() {
        return orderItemDetailTable;
    }
}
