package com.example.digital_attendance
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
class BackgroundWorker1(private val context: Context, private val callback: () -> Unit) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String {
        val url = URL(params[0])
        val table = params[1]
        val students = params[2]
        val newColumn = params[3]
        val status = params[4]
        val unselectedStudents = params[5]
        val menuid = params[6]

        val postData = "table=$table&students=$students&new_column=$newColumn&status=$status&unselectedStudents=$unselectedStudents&menuid=$menuid"
        Log.e("PostData",postData)
        if (students != null) {
            Log.e("Students",students)
        }

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
        // You can perform any necessary post-execution operations here
        Log.d("BackgroundWorker", "Result: $result")
        callback.invoke()
    }
}
