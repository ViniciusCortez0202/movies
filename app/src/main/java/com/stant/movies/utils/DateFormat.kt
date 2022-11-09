package com.stant.movies.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormat {
    companion object {
        fun getDateInAnotherFormat(inputFormat: String,outputFormat: String, date: String):String =
            SimpleDateFormat(inputFormat, Locale.getDefault()).parse(date)?.let { SimpleDateFormat(outputFormat,
                Locale.getDefault()).format(it) }?:""
    }

}