package model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class ColumnWidthUtil {
    public static void adjustColumnWidths(JTable table, int[] columnsToResize) {
        for (int columnIndex : columnsToResize) {
            TableColumn tableColumn = table.getColumnModel().getColumn(columnIndex);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, columnIndex);
                Component c = table.prepareRenderer(cellRenderer, row, columnIndex);
                int width = c.getPreferredSize().width;
                preferredWidth = Math.max(preferredWidth, width);


                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth( preferredWidth );
        }
    }
}
