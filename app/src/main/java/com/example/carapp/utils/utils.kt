package com.example.carapp.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

fun Context.getJsonFromAssets(
    fileName: String?,
): String? {
    val jsonString: String = try {
        val `is`: InputStream = this.assets.open(fileName!!)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return jsonString
}