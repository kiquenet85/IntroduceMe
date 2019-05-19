package com.kiquenet.introduceme.ui.util

import android.content.Context
import android.util.Log

import java.io.*
import java.nio.charset.Charset

/**
 * @author n.diazgranados
 */
object FileUtil {

    fun readFile(context: Context, resId: Int): Reader {
        val inputStream = context.resources.openRawResource(resId)
        return InputStreamReader(inputStream, Charset.forName("UTF-8"))
    }

    fun saveLastCatalogData(context: Context, jsonData: String) {
        val filename = "CatalogData"
        val outputStream: FileOutputStream

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(jsonData.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    fun readSavedCatalogData(context: Context): String? {
        var input: BufferedReader?
        var file: File?
        var result: String? = null
        try {
            file = File(context.filesDir, "CatalogData")

            if (!file.exists()) {
                return result
            }

            input = BufferedReader(InputStreamReader(FileInputStream(file)))
            var line = String()
            val buffer = StringBuffer()
            while (line.apply { line = input.readLine() } != null) {
                buffer.append(line)
            }

            result = buffer.toString()
            Log.d(context.javaClass.simpleName, result)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }
}
