package com.afterqcd.printer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by afterqcd on 2017/12/6.
 */
public class ColumnAlignerTest {
    private static final Double DELTA = 0.0000001;

    @Test
    public void testOneColumn() throws Exception {
        double[] factors = ColumnAligner.calcAlignScaleFactors(new double[] { 1.5 });
        Assert.assertArrayEquals(new double[] { 1.0 }, factors, DELTA);
    }

    @Test
    public void testTwoColumns() throws Exception {
        double[] factors = ColumnAligner.calcAlignScaleFactors(new double[] { 1.5, 1.5 });
        Assert.assertArrayEquals(new double[] { 1.0, 2.0 }, factors, DELTA);
        factors = ColumnAligner.calcAlignScaleFactors(new double[] { 1.0, 3.0 });
        Assert.assertArrayEquals(new double[] { 1.0, 4.0 }, factors, DELTA);
    }

    @Test
    public void testMultipleColumns() throws Exception {
        double[] factors = ColumnAligner.calcAlignScaleFactors(new double[] { 1.0, 2.0, 3.0 });
        Assert.assertArrayEquals(new double[] { 1.0, 2.0, 6.0 }, factors, DELTA);
    }
}
