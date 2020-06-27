package com.example.androidkotlindemo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

/**
 *  👇   ☟ ☝ ↓㏘
 */
open class MainActivity : AppCompatActivity() {

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            coroutineScope.launch {
                val bitmap = suspendingGetBitmap()// 网络请求，后台线程
                imageView1.setImageBitmap(bitmap)// 更新 UI 主线程
            }
        }
        button1.setOnClickListener {
            coroutineScope.launch {
                launch(Dispatchers.IO) {
                    //耗时
                }
                launch(Dispatchers.Main) {
                    val avatar = async { getAvatar() }//获取用户头像
                    val logo = async { getLogo() }//获取 Logo
                    mergeShowUI(avatar.await(), logo.await())//合并展示
                }
            }
        }
        button2.setOnClickListener {
            coroutineScope.cancel()
        }

        button3.setOnClickListener {
            coroutineScope = CoroutineScope(Dispatchers.Main)
        }
        coroutineScope.launch {
            suspendingFun()
        }
    }

    private suspend fun suspendingFun() {
        println("Current Thread: ${Thread.currentThread().name}")
    }

    private fun mergeShowUI(avatar: Bitmap, logo: Bitmap) {
        avatarIv.setImageBitmap(avatar)
        logoIv.setImageBitmap(logo)
    }

    private suspend fun getAvatar() = withContext(Dispatchers.IO) {
        val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200626111013.jpg")
        val openConnection = url.openConnection() as HttpURLConnection

        BitmapFactory.decodeStream(openConnection.inputStream)
    }

    private suspend fun getLogo(): Bitmap {
        return withContext(Dispatchers.IO) {
            val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200422102635.png")
            val openConnection = url.openConnection() as HttpURLConnection
            delay(1000*5)
            BitmapFactory.decodeStream(openConnection.inputStream)
        }
    }

    private suspend fun suspendingGetBitmap(): Bitmap {
        return withContext(Dispatchers.IO) {
            val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200420120447.png")
            val openConnection = url.openConnection() as HttpURLConnection
            BitmapFactory.decodeStream(openConnection.inputStream)
        }
    }
}




