package model.MenuItem;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Order.Order;

/**
 * A table model used to display menu items. This table contains two additional
 * rows at the bottom, one is an empty spacer and the other shows totals
 * information.
 */
public class BillTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "Quantity",
        "Name",
        "Price each (AUD)",
        "Energy (kj)",
        "Protein (g)",
        "Carbohydrates (g)",
        "Fat (g)",
        "Fibre (g)",
        "Total (AUD)",};

    private List<BillItem> billItems;
    
    public void setBillTableModel(List<BillItem> billItems) {
        this.billItems = billItems;
    }
    
    public BillTableModel(List<BillItem> billItems) {
        this.billItems = billItems;
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
     * Returns the amount of rows to display in the table. Don't display the
     * totals row if there are no menu items being displayed.
     *
     * @return Amount of rows to display in the table.
     */
    @Override
    public int getRowCount() {
        return billItems.size();
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
     * Returns boolean representing if the cell is editable. Cells are never
     * editable in this implementation.
     *
     * @param row The row of the cell.
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
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @return The data for the cell.
     */
    @Override
    public Object getValueAt(int row, int column) {

        // Check if we are displaying a menu item (as opposed to one of the totals rows).
        if (row < billItems.size()) {
            BillItem billItem = billItems.get(row);
            switch (column) {
                case 0:
                    return billItem.getQuantity();
                case 1:
                    return billItem.getName();
                case 2:
                    return billItem.getPrice();
                case 3:
                    return billItem.getEnergy() * billItem.getQuantity();
                case 4:
                    return String.format("%1.2f",billItem.getProtean() * billItem.getQuantity());
                case 5:
                    return String.format("%1.2f",billItem.getCarbohydrates() * billItem.getQuantity());
                case 6:
                    return String.format("%1.2f",billItem.getFat() * billItem.getQuantity());
                case 7:
                    return String.format("%1.2f",billItem.getFibre() * billItem.getQuantity());
                case 8:
                    return billItem.getTotalPrice();
                default:
                    return "undefined";
            }
        }
        return "undefined";
    }
}
