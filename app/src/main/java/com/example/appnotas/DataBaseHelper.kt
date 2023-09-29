package com.example.appnotas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION  ){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE ${Constants.ENTITY_NOTE} " +
                "( ${Constants.PROPERTY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.PROPERTY_DESCRIPTION} VARCHAR(100), " +
                "${Constants.PROPERTY_IS_FINISHED} BOOLEAN)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}