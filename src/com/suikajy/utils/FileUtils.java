package com.suikajy.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface FileUtils {

    static int[] readInts(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            List<Integer> list = new ArrayList<>();
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
                scanner.next();
            }
            int[] arr = new int[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i);
            }
            return arr;
        } catch (FileNotFoundException e) {
            System.out.println("不能打开文件" + fileName);
            return null;
        }
    }

    static void redirectIn(String inputFileName) {
        try {
            System.setIn(new FileInputStream(inputFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void redirectIO(String inputFileName, String outputFileName) {
        InputStream in = System.in;
        PrintStream out = System.out;
        try {
            System.setIn(new FileInputStream(inputFileName));
            System.setOut(new PrintStream(outputFileName));
        } catch (FileNotFoundException e) {
            System.out.println("未找到文件");
            e.printStackTrace();
        }
    }
}
