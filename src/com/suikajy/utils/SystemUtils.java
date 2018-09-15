package com.suikajy.utils;

import java.io.IOException;

public interface SystemUtils {

    static void dir() {
        try {
            Runtime.getRuntime().exec("dir");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
