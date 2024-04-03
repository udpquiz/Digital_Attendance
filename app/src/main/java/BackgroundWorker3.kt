import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

data class AttendanceResult(val totalCountP: Int, val totalCountA: Int, val attendanceData: Map<String, String>, val percentage: Int)

class BackgroundWorker3(private val context: Context, private val callback: (AttendanceResult) -> Unit) : AsyncTask<String, Void, AttendanceResult>() {

    override fun doInBackground(vararg params: String?): AttendanceResult {
        val url = URL(params[0])
        val table = params[1]
        val enrollment = params[2]

        val postData = "table=$table&enrollment=$enrollment"
        Log.e("PostData", postData)

        var totalCountP = 0
        var totalCountA = 0
        var percentage = 0
        var attendanceData = emptyMap<String, String>()
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
            totalCountP = jsonObject.getInt("total_count_P")
            totalCountA = jsonObject.getInt("total_count_A")
            // Retrieve the attendance data object
            val attendanceDataObject = jsonObject.getJSONObject("attendance_data")
            // Convert attendance data object to map
            val keys = attendanceDataObject.keys()
            while (keys.hasNext()) {
                val key = keys.next() as String
                val value = attendanceDataObject.getString(key)
                attendanceData += key to value
            }
            percentage = jsonObject.getInt("percentage_P")
            Log.d("Messages", "$totalCountA $totalCountP, $percentage")

        } catch (e: Exception) {
            Log.e("BackgroundWorker", "Error: ${e.message}")
        } finally {
            connection?.disconnect()
        }

        return AttendanceResult(totalCountP, totalCountA, attendanceData, percentage)
    }

    override fun onPostExecute(result: AttendanceResult?) {
        super.onPostExecute(result)
        if (result != null) {
            callback.invoke(result)
        }
    }
}
