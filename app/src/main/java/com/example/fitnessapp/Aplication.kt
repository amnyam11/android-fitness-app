package com.example.fitnessapp

import android.app.Application
import android.util.Log
import com.example.fitnessapp.db.AppDatabase


class Aplication : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
        Log.d("App", "Database init: ${db.openHelper.databaseName}")
    }
}