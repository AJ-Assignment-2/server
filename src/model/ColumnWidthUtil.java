package model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Class containing a static table column resizing method.
 */
public class ColumnWidthUtil {
    /**
     * Adjusts table column to fit text
     * @param tableToAdjust table whose columns should be adjusted
     * @param columnsToResize index of columns that need to be resized
     */
    public static void adjustColumnWidths(JTable tableToAdjust, int[] columnsToResize) {
        // Loop over all provided column indexes
        for (int columnIndex : columnsToResize) {
            // Get the current column and its max and min width.
            TableColumn currentColumn = tableToAdjust.getColumnModel().getColumn(columnIndex);
            int maximumW = currentColumn.getMaxWidth();
            int prefW = currentColumn.getMinWidth();

            // loop through all rows in the column finding the largest preferred and valid width
            for (int currentRow = 0; currentRow < tableToAdjust.getRowCount(); currentRow++)
            {
                TableCellRenderer tableCellRenderer = tableToAdjust.getCellRenderer(currentRow, columnIndex);
                Component cell = tableToAdjust.prepareRenderer(tableCellRenderer, currentRow, columnIndex);
                int width = cell.getPreferredSize().width;
                prefW = Math.max(prefW, width);
                if (prefW > maximumW)
                {
                    prefW = maximumW;
                    break;
                }
            }

            currentColumn.setPreferredWidth(prefW);
        }
    }
}
