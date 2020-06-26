package com.example.androidkotlindemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhanglulu on 2020/6/26.
 * for
 */
class TestJava {
    public static File readFile(File file, InputStream is) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[1024];
        int len;
        int total = 0;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            fos.flush();
            total = total + len;
        }
        fos.close();
        bis.close();
        is.close();
        return file;
    }
}
