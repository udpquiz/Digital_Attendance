import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class BackgroundWorker(private val context: Context, private val onCompleteListener: () -> Unit) : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String? {
        val url = params[0]
        val table = params[1]
        val enrollment = params[2]
        val name = params[3]
        try {
            val url = URL(url)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doOutput = true
            httpURLConnection.doInput = true
            val outputStream: OutputStream = httpURLConnection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            val postData: String =
                URLEncoder.encode("table", "UTF-8") + "=" +
                        URLEncoder.encode(table, "UTF-8") + "&" +
                        URLEncoder.encode("enrollment_Number", "UTF-8") + "=" +
                        URLEncoder.encode(enrollment, "UTF-8") + "&" +
                        URLEncoder.encode("Sname", "UTF-8") + "=" +
                        URLEncoder.encode(name, "UTF-8")
            bufferedWriter.write(postData)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()
            val inputStream: InputStream = httpURLConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
            var result = ""
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()

            return result
        } catch (e: MalformedURLException) {
            Log.e("Back", "{$e.message}")
        } catch (e: IOException) {
            Log.e("Back", "{$e.message}")
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // Notify the listener that the task is complete
        onCompleteListener()
    }
}
