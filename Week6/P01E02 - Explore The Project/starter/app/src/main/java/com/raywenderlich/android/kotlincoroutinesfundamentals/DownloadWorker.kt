package com.raywenderlich.android.kotlincoroutinesfundamentals

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    /**
     *   This function describes the type of work the WorkManager will run
     *   [Result] can be success or failure
     */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun doWork(): Result {
        //Download the Owl image from the internet
        val owlUrl = URL("https://wallpaperplay.com/walls/full/1/c/7/38027.jpg")
        val connection = owlUrl.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val imagePath = "owl_image.jpg"
        val inputStream = connection.inputStream
        //Create a file to store the image in
        val file = File(applicationContext.externalMediaDirs.first(), imagePath)


        val outputStream = FileOutputStream(file)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024)
            var byteCount = inputStream.read(buffer)

            while (byteCount > 0) {
                output.write(buffer, 0, byteCount)

                byteCount = inputStream.read(buffer)
            }
            output.flush()
        }
        return Result.success()
    }
}