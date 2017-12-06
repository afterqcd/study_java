package com.afterqcd.printer;

/**
 * Created by afterqcd on 2017/12/6.
 */
public class ColumnAligner {
    public static double[] calcAlignScaleFactors(double[] columnWidths) {
        checkColumnWidths(columnWidths);

        Double totalWidth = widthSum(columnWidths, columnWidths.length);
        double[] factors = new double[columnWidths.length];
        for (int columns = columnWidths.length; columns > 0; columns--) {
            double width = widthSum(columnWidths, columns);
            double factor = totalWidth / width;
            factors[columnWidths.length - columns] = factor;
        }
        return factors;
    }

    private static void checkColumnWidths(double[] columnWidths) {
        if (columnWidths == null || columnWidths.length == 0) {
            throw new IllegalArgumentException("Array of column widths can not be null of empty");
        }
    }

    private static double widthSum(double[] columnWidths, int columns) {
        double sum = 0D;
        for (int index = 0; index < columns; index++) {
            sum += columnWidths[index];
        }
        return sum;
    }
}
