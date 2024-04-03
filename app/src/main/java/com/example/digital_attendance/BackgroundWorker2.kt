package com.example.digital_attendance

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class BackgroundWorker2(private val context: Context, private val callback: () -> Unit) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String {
        val url = URL(params[0])
        val table = params[1]
        val column = params[2]
        val enrollment = params[3]

        val postData = "table=$table&column=$column&enrollment=$enrollment"
        Log.e("PostData Back2",postData)
        var result = ""
        var connection: HttpURLConnection? = null
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true

            val wr = OutputStreamWriter(connection.outputStream)
            wr.write(postData)
            wr.flush()

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val stringBuilder = StringBuilder()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }

            reader.close()
            result = stringBuilder.toString()
        } catch (e: Exception) {
            Log.e("BackgroundWorker", "Error: ${e.message}")
        } finally {
            connection?.disconnect()
        }

        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // Show the fetched result after the Toast messages
        if (!result.isNullOrEmpty()) {
            Toast.makeText(context, "Attendance Status: $result", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No data fetched", Toast.LENGTH_SHORT).show()
        }

        // You can perform any additional post-execution operations here
        Log.d("BackgroundWorker", "Result: $result")
        callback.invoke()
    }
}
