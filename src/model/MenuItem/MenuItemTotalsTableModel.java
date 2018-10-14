package model.MenuItem;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

public class MenuItemTotalsTableModel extends AbstractTableModel {
    private String[] columnNames = {
            "Name",
            "Price (AUD)",
            "Energy (kj)",
            "Protein (g)",
            "Carbohydrates (g)",
            "Fat (g)",
            "Fibre (g)",
            "Type",
            "Category"
    };

    private List<MenuItem> menuItems;

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuItemTotalsTableModel(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
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
     * Don't display the totals row if there are no menu items being displayed.
     *
     * @return Amount of rows to display in the table.
     */
    @Override
    public int getRowCount() {
        if (menuItems.size() > 0) {
            return menuItems.size() + 2;
        } else {
            return menuItems.size();
        }
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

        // Check if we are displaying a menu item (as opposed to one of the totals rows.
        if (row < menuItems.size()) {
            MenuItem menuItem = menuItems.get(row);
            switch (column) {
                case 0:
                    return menuItem.getName();
                case 1:
                    return menuItem.getPrice();
                case 2:
                    return menuItem.getEnergy();
                case 3:
                    return menuItem.getProtean();
                case 4:
                    return menuItem.getCarbohydrates();
                case 5:
                    return menuItem.getFat();
                case 6:
                    return menuItem.getFibre();
                case 7:
                    return menuItem.getType();
                case 8:
                    return menuItem.getCategory();
                default:
                    return "undefined";
            }
        } else if (row == getRowCount() - 2) {
            return "";
        } else if (row == getRowCount() - 1) {
            int priceTotal = 0;
            int energyTotal = 0;
            double proteanTotal = 0;
            double carbTotal = 0;
            double fatTotal = 0;
            double fibreTotal = 0;

            for (MenuItem item : menuItems) {
                priceTotal += item.getPrice();
                energyTotal += item.getEnergy();
                proteanTotal += item.getProtean();
                carbTotal += new BigDecimal(item.getCarbohydrates()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                fatTotal += new BigDecimal(item.getFat()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                fibreTotal += new BigDecimal(item.getFibre()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            switch (column) {
                case 0:
                    return "Totals:";
                case 1:
                    return priceTotal;
                case 2:
                    return energyTotal;
                case 3:
                    return String.format("%1.2f", proteanTotal);
                case 4:
                    return String.format("%1.2f", carbTotal);
                case 5:
                    return String.format("%1.2f", fatTotal);
                case 6:
                    return String.format("%1.2f", fibreTotal);
                default:
                    return "";
            }
        }
        return "undefined";
    }
}
