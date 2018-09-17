package com.suikajy.ch1_basics.matrix;

public class Matrix {

    public static void main(String[] args) {
        double[][] a = {{1, 0, 2}, {-1, 3, 1}},
                b = {{3, 1}, {2, 1}, {1, 0}};
        double[][] result = mult(a, b);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * 向量点乘
     */
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("向量x和y不同维");
        }
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

    /**
     * 矩阵和矩阵的乘积
     * <p>
     * a的第i行点乘b的第j列的值作为结果矩阵c第i行第j列的值
     */
    public static double[][] mult(double[][] a, double[][] b) {
        if (a.length == 0 || a[0].length == 0 || b.length == 0 || b[0].length == 0)
            throw new IllegalArgumentException("非法矩阵");
        if (a[0].length != b.length)
            throw new IllegalArgumentException("a的列数不等于b的行数，不同纬度的向量无法点乘");
        final int resultRowCount = a.length;//结果的行数等于a的行数
        final int resultColCount = b[0].length;//结果的列数等于b的列数
        final int productDimension = a[0].length;//进行结果乘积时向量乘积的维度

        double[][] result = new double[resultRowCount][resultColCount];

        for (int i = 0; i < resultRowCount; i++) {
            for (int j = 0; j < resultColCount; j++) {
                for (int k = 0; k < productDimension; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    /**
     * 矩阵转置
     * <p>
     * 矩阵转置即将原有的a[i][j]变成了转置矩阵的b[j][i]
     */
    public static double[][] transpose(double[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0) throw new IllegalArgumentException("非法矩阵参数");
        double[][] result = new double[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[j][i] = a[i][j];
            }
        }
        return result;
    }

    /**
     * 矩阵和向量之积
     * <p>
     * 这里的向量相当于单列矩阵
     */
    public static double[] mult(double[][] a, double[] x) {
        if (a == null || x == null || a.length == 0 || x.length == 0 || a[0].length == 0)
            throw new IllegalArgumentException("非法矩阵参数");
        if (a[0].length != x.length)
            throw new IllegalArgumentException("矩阵列数不等于向量元素个数，不可乘");
        double[] resultVector = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                resultVector[i] += a[i][j] * x[j];
            }
        }
        return resultVector;
    }

    /**
     * 向量和矩阵之积
     * <p>
     * 这里的向量相当于单行矩阵
     */
    public static double[] mult(double[] y, double[][] a) {
        if (a == null || y == null || a.length == 0 || y.length == 0 || a[0].length == 0)
            throw new IllegalArgumentException("非法矩阵参数");
        if (y.length != a.length)
            throw new IllegalArgumentException("矩阵列数不等于向量元素个数，不可乘");
        double[] resultVector = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y.length; j++) {
                resultVector[i] += y[j] * a[j][i];
            }
        }
        return resultVector;
    }

}
