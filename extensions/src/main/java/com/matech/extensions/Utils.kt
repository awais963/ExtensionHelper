package com.matech.extensions

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.text.format.DateFormat
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

fun setGradientTextColor(textView: TextView) {
    val paint = textView.paint
    val width = paint.measureText(textView.text.toString())
    val textShader: Shader = LinearGradient(
        0f, 0f, 0f, textView.textSize, intArrayOf(
            Color.parseColor("#FF9D02"),
            Color.parseColor("#FF5A01")
        ), null, Shader.TileMode.CLAMP
    )

    textView.paint.shader = textShader
}

fun formatDateTime(timeInMilli: Long): String {
    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())/*.apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }*/
    return outputDateFormat.format(timeInMilli)
}

fun formatDate(timeInMilli: Long): String {
    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())/*.apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }*/
    return outputDateFormat.format(timeInMilli)
}

fun formatMonth(timeInMilli: Long): String {
    val outputDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())/*.apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }*/
    return outputDateFormat.format(timeInMilli)
}

fun isDobValid(timeInMilli: Long): Boolean {

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val date = Date(timeInMilli)
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.time = date
    val inputYear = calendar.get(Calendar.YEAR)
    return currentYear - inputYear >= 13
}

fun getDeviceName(): String? {
    val manufacturer = Build.MANUFACTURER
    val brand = Build.BRAND
    val model = Build.MODEL
    return if (model.startsWith(manufacturer)) {
        model
    } else "$manufacturer $brand $model"

}

// A placeholder email validation check
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// A placeholder password validation check
fun isPasswordValid(password: String): String? {

    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,}$".toRegex())) {
        return "Minimum seven characters, at least one letter small and capital, one number and one special character"
    }

    return null
}

// A placeholder username validation check
fun isUsernameValid(username: String): Boolean {
    return username.length > 2
}


// Error Message
fun responseErrorMessage(errorBody: ResponseBody?): String {

    return try {
        val errorObject =
            errorBody?.charStream()?.readText()?.let { JSONObject(it) }
        errorObject?.getString("message") ?: ""
    } catch (e: Exception) {
        Log.e("Exception", e.message.toString())
        ""
    }
}


/*fun convertRankCoins(coins: Int): String {
    return when (coins) {
        in 1000..999999 -> {
            val temp = coins / 1000
            val tempResult = temp.toString()
            val result: String = tempResult + "K"
            result
        }
        in 1000000..99999999 -> {
            val temp = coins / 1000000
            val tempResult = temp.toString()
            return tempResult + "M"
        }
        in 1000000000..100000000000 -> {
            val temp = coins / 10000000
            val tempResult = temp.toString()
            return tempResult + "B"
        }
        else -> coins.toString()
    }
}*/


fun stringArrayToIntArray(value: List<String>): List<Int> {
    val coins = ArrayList<Int>()

    for (element in value) {
        //coins[i] = Integer.parseInt(result.coin[i])
        coins.add(element.toInt())
    }
    return coins
}

const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val MONTH_YEAR_FORMAT = "MMMM yyyy"
val locale: Locale = Locale.ENGLISH
fun profileDateFormat(input: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val inputFormatter =
            DateTimeFormatter.ofPattern(ISO_DATE_FORMAT, locale)
        val outputFormatter = DateTimeFormatter.ofPattern(MONTH_YEAR_FORMAT, locale)
        val date = LocalDate.parse(input, inputFormatter)
        val formattedDate = outputFormatter.format(date)
        formattedDate.toString()

    } else {
        val parser = SimpleDateFormat(ISO_DATE_FORMAT, locale)
        val formatter = SimpleDateFormat(MONTH_YEAR_FORMAT, locale)
        val formattedDate = formatter.format(parser.parse(input)!!)
        formattedDate.toString()
    }
}

fun chatDateFormat(input: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            val inputFormatter =
                Instant.parse(input)
            val time = chatDateFormat(inputFormatter.toEpochMilli())
            time

        } catch (e: Exception) {
            input
        }

    } else {
        val parser = SimpleDateFormat(ISO_DATE_FORMAT, locale)
        val formatter = SimpleDateFormat("HH:mm", locale)
        val formattedDate = formatter.format(parser.parse(input)!!)
        formattedDate.toString()
    }
}

fun chatDateFormat(input: Long): String {

    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = input

    return DateFormat.format("hh:mm a", cal).toString()

}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}

fun timerTimeStamp(endTime: Long, currentTime: Long) = (endTime.minus(currentTime)) * 1000

gi
fun returnNonZero(value: Double?): Int {
    return if (value?.roundToInt() == 0) 1
    else (value?.roundToInt() ?: 1)
}

fun timestampToLocalDateTime(time: Long): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = time

    return DateFormat.format("hh:mm a dd MMM", cal).toString()

}

fun timestampToDate(time: Long): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = time

    return DateFormat.format("dd MMM", cal).toString()

}

fun timestampToTime(time: Long): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = time

    return DateFormat.format("hh:mm a", cal).toString()

}


fun checkRooted(): Boolean {
    return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
}

private fun checkRootMethod1(): Boolean {
    val buildTags = Build.TAGS
    return buildTags != null && buildTags.contains("test-keys")
}

private fun checkRootMethod2(): Boolean {
    val paths = arrayOf(
        "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
        "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
        "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"
    )
    for (path in paths) {
        if (File(path).exists()) return true
    }
    return false
}

private fun checkRootMethod3(): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
        val `in` = BufferedReader(InputStreamReader(process.inputStream))
        `in`.readLine() != null
    } catch (t: Throwable) {
        false
    } finally {
        process?.destroy()
    }
}


fun prettyCount(coins: Long): String {
    return when (coins) {
        in 1000..999999 -> {
            val temp = coins / 1000
            val tempResult = temp.toString()
            val result: String = tempResult + "K"
            result
        }
        in 1000000..99999999 -> {
            val temp = coins / 1000000
            val tempResult = temp.toString()
            return tempResult + "M"
        }
        in 1000000000..100000000000 -> {
            val temp = coins / 1000000000
            val tempResult = temp.toString()
            return tempResult + "B"
        }
        else -> coins.toString()
    }
}

fun convertCoinsCount(coins: Long): String {
    return when (coins) {
        in 1000..999999 -> {
            val temp = coins / 1000.0
            val tempResult = if (temp % 1.0 == 0.0) {
                String.format("%.0f", temp)
            } else {
                String.format("%.1f", temp)
            }

            val result: String = tempResult + "K"
            result
        }
        in 1000000..99999999 -> {
            val temp = coins / 1000000.0
            val tempResult = String.format("%.1f", temp)
            return tempResult + "M"
        }
        in 1000000000..100000000000 -> {
            val temp = coins / 1000000000
            return "$temp B"

        }
        else -> coins.toString()
    }
}
