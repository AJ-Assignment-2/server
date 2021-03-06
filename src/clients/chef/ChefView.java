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

/**
 * The chef view that comprises mainly of two jtables displaying orders in different states.
 * The third JTable displays a selected order's menu item details.
 */
public class ChefView extends JFrame{
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuAbout;
    
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

    /**
     * Initialise the different components contained within this view.
     */
    public ChefView(){
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        this.menuAbout = new JMenuItem("About");
        menuFile.add(menuAbout);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);
        
        
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
                int row = servedOrdersTable.rowAtPoint(e.getPoint());
                servedOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                prepareButton.setEnabled(false);
            }
        });

        // Always highlight the entire row
        waitingOrdersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = waitingOrdersTable.rowAtPoint(e.getPoint());
                waitingOrdersTable.getSelectionModel().setSelectionInterval(row, row);
                prepareButton.setEnabled(true);
            }
        });

        add(rootPanel);
    }

    /**
     * A method to execute listener for menu button
     * 
     * @param menuAboutListener containing a listener of exit button
     */
    public void addMenuAboutListener(ActionListener menuAboutListener) {
        menuAbout.addActionListener(menuAboutListener);
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
     * Get the table containing an order's menu items.
     * @return JTable containing selected order's menu items.
     */
    public JTable getOrderDetailsTable() {
        return orderDetailsTable;
    }

    /**
     * Get the table containing all waiting orders.
     * @return The table used to display all waiting orders.
     */
    public JTable getWaitingOrdersTable() {
        return waitingOrdersTable;
    }

    /**
     * Get the table containing all served orders.
     * @return The table used to display all served orders.
     */
    public JTable getServedOrdersTable() {
        return servedOrdersTable;
    }

    /**
     * Add listener's to the prepare button.
     * @param prepareButtonListener The listener to add
     */
    public void addPrepareButtonListener(ActionListener prepareButtonListener){
        prepareButton.addActionListener(prepareButtonListener);
    }

    /**
     * Add listener for row selection events.
     * @param listener The listener to add
     */
    public void addWaitingOrdersRowSelectedListener(ListSelectionListener listener) {
        waitingOrdersTable.getSelectionModel().addListSelectionListener(listener);
    }

    /**
     * Add listener for row selection events.
     * @param listener The listener to add
     */
    public void addServedOrdersRowSelectedListener(ListSelectionListener listener) {
        servedOrdersTable.getSelectionModel().addListSelectionListener(listener);
    }
}
