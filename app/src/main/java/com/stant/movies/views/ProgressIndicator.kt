package com.stant.movies.views

import android.app.Activity
import android.app.AlertDialog
import views.R


class ProgressIndicator(activity: Activity){

    private var activity: Activity
    private lateinit var alert: AlertDialog

    init {
        this.activity = activity
    }

    fun startLoading(){
        var builder = AlertDialog.Builder(activity)

        var inflatActivity = activity.layoutInflater
        builder.setView(inflatActivity.inflate(R.layout.loading, null))
        builder.setCancelable(true)

        alert = builder.create()
        alert.show()
    }

    fun close(){
        alert.dismiss()
    }

}