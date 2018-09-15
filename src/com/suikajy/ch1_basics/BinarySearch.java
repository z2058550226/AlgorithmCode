package com.suikajy.ch1_basics;

import com.suikajy.utils.FileUtils;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Objects;

public class BinarySearch {

    public static int rank(int key, int[] a) {
        // 数组必须是有序的
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    static {
        FileUtils.redirectIn("algs_res/tinyT.txt");
    }

    public static void main(String[] args) {
        int[] whitelist = In.readInts("algs_res/tinyW.txt");
        Arrays.sort(Objects.requireNonNull(whitelist));
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, whitelist) < 0) {
                StdOut.println(key);
            }
        }
        double a = 2.0e-6;
        System.out.println(a * 1000000000.1);
        boolean b = true && false || true && true;
        System.out.println(b);
    }
}
