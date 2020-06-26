package com.example.androidkotlindemo

import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Created by zhanglulu on 2020/6/26.
 * for
 */
class TestKotlin {
    public fun readFile(file: File,inputStream: InputStream) {
        val fos = FileOutputStream(file)
        val bis = BufferedInputStream(inputStream)
        val buffer = ByteArray(1024)
        var len: Int
        while (((bis.read(buffer)).also { len = it }) != -1) {
            fos.write(buffer, 0, len)
            fos.flush()
        }
        fos.close()
    }
}