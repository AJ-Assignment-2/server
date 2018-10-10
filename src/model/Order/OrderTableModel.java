package model.Order;

import model.MenuItem.MenuItem;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "Customer Name",
            "Table Number"
    };

    private List<Order> orders;

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    public Order getOrder(int row) {
        if (orders != null) {
            return orders.get(row);
        } else {
            return null;
        }
    }

    /**
     * Returns the amount of columns.
     *
     * @return The amount of columns.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the names of the different columns.
     *
     * @param columnIndex The index of the column to name.
     * @return The name of the column at the given index.
     */
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /**
     * Returns the amount of rows to display in the table.
     *
     * @return Amount of rows to display in the table.
     */
    @Override
    public int getRowCount() {
        return orders.size();
    }

    /**
     * Returns the class of data to be displayed in the column.
     *
     * @param columnIndex The column index to return a Class for.
     * @return The class of data contained under this column index.
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    /**
     * Returns boolean representing if the cell is editable.
     * Cells are never editable in this implementation.
     *
     * @param row    The row of the cell.
     * @param column The column of the cell.
     * @return A boolean representing if it can be edited. Always false.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Returns the value to display in a particular cell.
     *
     * @param row    The row of the cell.
     * @param column The column of the cell.
     * @return The data for the cell.
     */
    @Override
    public Object getValueAt(int row, int column) {
        Order order = orders.get(row);
        switch (column) {
            case 0:
                return order.getCustomerName();
            case 1:
                return order.getTableNumber();
            case 2:
                return order.getState();
            default:
                return "undefined";
        }
    }
}
