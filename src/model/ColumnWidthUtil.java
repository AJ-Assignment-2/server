package model;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ColumnWidthUtil {
    public static void adjustColumnWidths(JTable tableToAdjust, int[] columnsToResize) {
        for (int columnIndex : columnsToResize) {
            TableColumn currentColumn = tableToAdjust.getColumnModel().getColumn(columnIndex);
            int maximumW = currentColumn.getMaxWidth();
            int prefW = currentColumn.getMinWidth();

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
