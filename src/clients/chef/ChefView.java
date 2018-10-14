/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import model.MenuItem.MenuItemTableModel;
import model.Order.OrderTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;

public class ChefView extends JFrame{
    private Border border;
    
    private JPanel rootPanel;

    private JPanel orderStatusPanel;
    private JPanel waitingOrdersContainer;
    private JPanel preparedOrdersContainer;

    private JLabel waitingOrdersLabel;
    private JLabel servedOrdersLabel;
    private JLabel orderItemDetailsLabel;

    private JScrollPane waitingOrdersScrollPane;
    private JScrollPane servedOrdersScrollPane;
    private JScrollPane orderItemDetailsScrollPane;

    private JTable servedOrdersTable;
    private JTable waitingOrdersTable;
    private JTable orderDetailsTable;

    private JButton prepareButton;
    
    public ChefView(){
        rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        border = BorderFactory.createLineBorder(Color.BLACK);

        orderStatusPanel = new JPanel();
        orderStatusPanel.setLayout(new BoxLayout(orderStatusPanel, BoxLayout.X_AXIS));
        orderStatusPanel.setBorder(border);
        waitingOrdersContainer = new JPanel();
        waitingOrdersContainer.setLayout(new BoxLayout(waitingOrdersContainer, BoxLayout.Y_AXIS));
        preparedOrdersContainer = new JPanel();
        preparedOrdersContainer.setLayout(new BoxLayout(preparedOrdersContainer, BoxLayout.Y_AXIS));

        waitingOrdersLabel = new JLabel("Orders waiting to be prepared");
        servedOrdersLabel = new JLabel("Orders that have been prepared and served");
        orderItemDetailsLabel = new JLabel("Selected order details");

        prepareButton = new JButton("Prepare Order");
        prepareButton.setEnabled(false);

        waitingOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));
        servedOrdersTable = new JTable(new OrderTableModel(new ArrayList<>()));
        orderDetailsTable = new JTable(new MenuItemTableModel(new ArrayList<>()));
        orderDetailsTable.setCellSelectionEnabled(false);
        orderDetailsTable.setRowSelectionAllowed(false);

        waitingOrdersTable.getSelectionModel().addListSelectionListener(event -> orderDetailsTable.clearSelection());
        servedOrdersTable.getSelectionModel().addListSelectionListener(event -> waitingOrdersTable.clearSelection());

        waitingOrdersScrollPane = new JScrollPane(waitingOrdersTable);
        servedOrdersScrollPane = new JScrollPane(servedOrdersTable);
        orderItemDetailsScrollPane = new JScrollPane(orderDetailsTable);

        waitingOrdersContainer.add(waitingOrdersLabel);
        waitingOrdersContainer.add(waitingOrdersScrollPane);

        preparedOrdersContainer.add(servedOrdersLabel);
        preparedOrdersContainer.add(servedOrdersScrollPane);

        orderStatusPanel.add(waitingOrdersContainer);
        orderStatusPanel.add(preparedOrdersContainer);

        rootPanel.add(orderStatusPanel);
        rootPanel.add(orderItemDetailsLabel);
        rootPanel.add(orderItemDetailsScrollPane);
        rootPanel.add(prepareButton);

        // Always highlight the entire row
        servedOrdersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = servedOrdersTable.rowAtPoint(e.getPoint());
                servedOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                prepareButton.setEnabled(false);
            }
        });

        // Always highlight the entire row
        waitingOrdersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = waitingOrdersTable.rowAtPoint(e.getPoint());
                waitingOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                prepareButton.setEnabled(true);
            }
        });

        add(rootPanel);
    }

    public JTable getOrderDetailsTable() {
        return orderDetailsTable;
    }

    public JTable getWaitingOrdersTable() {
        return waitingOrdersTable;
    }

    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    public void addPrepareButtonListener(ActionListener prepareButtonListener){
        prepareButton.addActionListener(prepareButtonListener);
    }

    public void addWaitingOrdersRowSelectedListener(ListSelectionListener listener) {
        waitingOrdersTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addServedOrdersRowSelectedListener(ListSelectionListener listener) {
        servedOrdersTable.getSelectionModel().addListSelectionListener(listener);
    }
}
