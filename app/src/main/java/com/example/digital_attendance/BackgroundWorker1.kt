import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class BackgroundWorker1(private val context: Context, private val callback: (List<InsertedData>) -> Unit) : AsyncTask<String, Void, List<InsertedData>>() {

    override fun doInBackground(vararg params: String?): List<InsertedData> {
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

        var insertedDataList = mutableListOf<InsertedData>()
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
            val result = stringBuilder.toString()

            // Parse JSON response
            val jsonObject = JSONObject(result)
            if (jsonObject.has("inserted_data")) {
                val insertedDataArray = jsonObject.getJSONArray("inserted_data")
                for (i in 0 until insertedDataArray.length()) {
                    val insertedDataObject = insertedDataArray.getJSONObject(i)
                    val enrollmentNumber = insertedDataObject.getString("enrollment_Number")
                    val status = insertedDataObject.getString("status")
                    insertedDataList.add(InsertedData(enrollmentNumber, status))
                }
            }
        } catch (e: Exception) {
            Log.e("BackgroundWorker", "Error: ${e.message}")
        } finally {
            connection?.disconnect()
        }

        return insertedDataList
    }


    override fun onPostExecute(result: List<InsertedData>?) {
        super.onPostExecute(result)
        callback.invoke(result ?: emptyList())
    }
}
