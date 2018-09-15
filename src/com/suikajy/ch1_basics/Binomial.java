package com.suikajy.ch1_basics;

import com.suikajy.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * 该类实现了三种不同的二项分布概率求解的实现：
 * 1. 直接递归方式
 * 2. 带缓存的递归方式
 * 3. 数学公式法
 *
 * 1. 直接递归方式是通过数学归纳法最容易想到的编程方式。但缺点很明显
 * ——重复运算次数过多（100次硬币50次为正就要算很久），这体现了直接递归的重复运算的缺陷。
 * 2. 为了减少重复运算次数，相关联的每次运算缓存到一个二维数组中，这样使得算法的耗时和矩阵的容量成正比了
 * 这种方式已经非常快了(72万次30ms)，十分接近公式法。但数据量过大时会产生栈溢出异常。
 * 这时就体现了递归方式的局限性——依赖栈空间大小。
 * 3. 当数据量十分巨大的时候(这里用6000次硬币3000次为正测试)，第一种就不要想了（超时加栈溢出），第二种已经栈溢出（但速度还是很快）。
 * 这时就要使用循环了，循环天生附带缓存，并且不依赖栈空间大小。但循环的缺点就是对数学归纳法很不友好，不容易表达出运算的层级层次关系。
 * 这时就要利用数学知识总结出运算公式进行计算了。数学公式运算速度非常快（比第二种略快），因为已经是十分精简的算法了。
 * 但数学公式需要十分高的精度。程序语言的double和long都无法满足这种运算
 */
public class Binomial {

    public static void main(String[] args) {
        System.out.println("开始时间 = " + DateUtils.currentHMS());
        invokeNum = 0;
        final int N = 1200;
        final int k = 600;
        final double p = 0.5;
        initCacheArr(N, k, p);
        System.out.printf("%d次硬币，有%d次为正的概率：%f\n", N, k, binomial2(N, k, p));
//        System.out.println(C(N, k));
        System.out.println("invokeNum = " + invokeNum);
        System.out.println("结束时间 = " + DateUtils.currentHMS());
    }

    private static int invokeNum = 0;

    private static final int ILLEGAL_NUM = -1;

    private static double[][] sCalCacheArr;

    /**
     * 初始化矩阵用来缓存计算结果
     */
    private static void initCacheArr(int N, int k, double p) {
        sCalCacheArr = new double[N][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < k + 1; j++) {
                if (j == 0) {
                    sCalCacheArr[i][j] = Math.pow(1.0 - p, i + 1);
                } else if (i + 1 == j) {
                    sCalCacheArr[i][j] = Math.pow(p, j);
                } else {
                    sCalCacheArr[i][j] = ILLEGAL_NUM;
                }
            }
        }
    }

    /**
     * 二项分布递归算法
     * <p>
     * 二项分布：类似于投掷硬币10次求正面朝上3次的概率，N为10，k为3，p为0.5
     * <p>
     * 核心思想：利用递归的方式，计算出N-1个事件发生的概率，当减少一个实验次数时
     * 可能减少的是发生的，也可能是未发生的，发生的就用p * binomial(N - 1, k - 1, p)来计算，
     * 然后加上未发生的(1.0 - p) * binomial(N - 1, k, p)，就是N次实验的概率
     *
     * @param N 实验次数
     * @param k 事件发生次数
     * @param p 单次事件发生概率
     * @return N次实验发生k次事件的概率
     */
    private static double binomial(int N, int k, double p) {
        invokeNum++;
        if (N == 0 && k == 0) {
            System.out.println("必然事件");
            return 1.0d; // 必然事件
        }
        if (N < 0 && k < 0) {
            System.out.println("不可能事件");
            return 0.0d; // 不可能事件
        }
        if (N < k) throw new IllegalArgumentException("N < K");
        if (N == k) return Math.pow(p, N);
        if (k == 0) return Math.pow((1.0 - p), N);
        double result = (1.0 - p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
        //System.out.printf("N=%d, k=%d, p=%f, result=%f\n", N, k, p, result);
        return result;
    }

    /**
     * 带缓存的二项分布算法
     *
     * @param N 实验次数
     * @param k 事件发生次数
     * @param p 单次事件发生概率
     * @return N次实验发生k次事件的概率
     */
    private static double binomial2(int N, int k, double p) {
        invokeNum++;
        if (N == 0 && k == 0) return 1.0d; // 必然事件
        if (N < 0 && k < 0) return 0.0d; // 不可能事件
        double cachep = getCacheP(N, k);
        if (cachep != ILLEGAL_NUM) return cachep;
        double result = (1.0 - p) * binomial2(N - 1, k, p) + p * binomial2(N - 1, k - 1, p);
        setCacheP(N, k, result);
        //System.out.printf("N=%d, k=%d, p=%f, result=%f\n", N, k, p, result);
        return result;
    }

    private static double getCacheP(int N, int k) {
        return sCalCacheArr[N - 1][k];
    }

    private static void setCacheP(int N, int k, double result) {
        if (N <= 0 || k > N) return;
        sCalCacheArr[N - 1][k] = result;
    }

    /**
     * 二项分布公式法：事件发生概率为p的情况下，N次实验发生k次事件的概率为
     * P = p^k * (1-p)^(N-k) * (n! / k! * (n-k)!) / 2^n
     *
     * @param N 实验次数
     * @param k 事件发生次数
     * @param p 单次事件发生概率
     * @return N次实验发生k次事件的概率
     */
    private static double binomial3(int N, int k, double p) {
        if (N == 0 && k == 0) return 1.0d; // 必然事件
        if (N < 0 && k < 0) return 0.0d; // 不可能事件
        BigDecimal result = BigDecimal.valueOf(Math.pow(p, k)).multiply(BigDecimal.valueOf(Math.pow(1.0 - p, N - k)).multiply(C(N, k)));
        return result.doubleValue();
    }

    /**
     * 求num的阶乘
     */
    private static BigDecimal factorial(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) > 0) throw new IllegalArgumentException("illegal param " + num);
        if (num.compareTo(BigDecimal.valueOf(2)) < 0) return BigDecimal.ONE;
        return factorial(num.subtract(BigDecimal.ONE)).multiply(num);
    }

    /**
     * 组合数公式求C_n^m。
     * <p>
     * 尚不支持大数运算，当计算C_40^20的时候，由于20的阶乘超过了int的最大值
     * 所以只能用小数玩玩。
     */
    private static BigDecimal C(long n, long m) {
        if (m > n || n < 1 || m < 0)
            throw new IllegalArgumentException("Illegal n and m!");
        if (n == 1) return BigDecimal.ONE;
        if (m > n / 2) {
            m = n - m;
        }
        BigDecimal denominator = BigDecimal.ONE;
        BigDecimal numerator = BigDecimal.ONE;
        long i = 1;
        do {
            denominator = denominator.multiply(BigDecimal.valueOf(i));
            i++;
        } while (i <= m);
        i = n - m + 1;
        do {
            numerator = numerator.multiply(BigDecimal.valueOf(i));
            i++;
        } while (i <= n);
        return numerator.divide(denominator, RoundingMode.UNNECESSARY);
    }
}
