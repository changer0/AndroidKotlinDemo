package com.example.androidkotlindemo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = getImageBitmap()// 网络请求，后台线程
                imageView1.setImageBitmap(bitmap)// 更新 UI 主线程
            }
        }
        button1.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                launch(Dispatchers.IO) {
                    //耗时
                }
                launch(Dispatchers.Main) {
                    val avatar = async { getAvatar() }//获取用户头像
                    val logo = async { getLogo() }//获取 Logo
                    mergeShowUI(avatar, logo)//合并展示
                }

            }

        }


    }

    private suspend fun mergeShowUI(avatar: Deferred<Bitmap>, logo: Deferred<Bitmap>) {
        Toast.makeText(this, "${avatar.await()} ${logo.await()} 都回来了！", Toast.LENGTH_SHORT).show()
        avatarIv.setImageBitmap(avatar.await())
        logoIv.setImageBitmap(logo.await())
    }

    private suspend fun getAvatar(): Bitmap {
        return withContext(Dispatchers.IO) {
            val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200626111013.jpg")
            val openConnection = url.openConnection() as HttpURLConnection

            BitmapFactory.decodeStream(openConnection.inputStream)
        }
    }

    private suspend fun getLogo(): Bitmap {
        return withContext(Dispatchers.IO) {
            val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200422102635.png")
            val openConnection = url.openConnection() as HttpURLConnection
            delay(1000*5)
            BitmapFactory.decodeStream(openConnection.inputStream)
        }
    }

    private suspend fun getImageBitmap(): Bitmap {
        return withContext(Dispatchers.IO) {
            val url = URL("https://gitee.com/luluzhang/ImageCDN/raw/master/blog/20200420120447.png")
            val openConnection = url.openConnection() as HttpURLConnection
            BitmapFactory.decodeStream(openConnection.inputStream)
        }
    }
}




