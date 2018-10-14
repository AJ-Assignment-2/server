package model.MenuItem;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class MenuItemColumnWidthRenderer extends JTextArea implements TableCellRenderer {

    public MenuItemColumnWidthRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        setText((value == null) ? "" : value.toString());
        setEditable(false);

        TableColumn currentColumn = table.getColumnModel().getColumn(column);
        int preferredWidth = currentColumn.getMinWidth();

        int maxWidth = currentColumn.getMaxWidth();
        int width = currentColumn.getPreferredWidth() + table.getIntercellSpacing().width;
        preferredWidth = Math.max(preferredWidth, width);

        //  We've exceeded the maximum width, no need to check other rows
        if (preferredWidth >= maxWidth) {
            preferredWidth = maxWidth;
            table.getColumnModel().getColumn(column).setPreferredWidth(preferredWidth);
        }


        return this;
    }
}
