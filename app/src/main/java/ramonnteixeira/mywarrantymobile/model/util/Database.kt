package com.example.todounibavekotlin.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.nio.charset.Charset

class Database(var context: Context, private val const: DatabaseConstants = DatabaseConstants())
    : SQLiteOpenHelper(context, const.dbName, null, const.dbVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(loadScript("create"))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val numberPattern = "%03d"
        for (i in oldVersion..newVersion) {
            db!!.execSQL(
                loadScript(
                    "V__${numberPattern.format(i)}.sql"
                )
            )
        }
    }

    private fun loadScript(filename: String): String {
        context.assets.list("").iterator().forEach { s -> println(s) }
        context.assets.list("migrations").iterator().forEach { s -> println(s) }

        val file = context.assets.open("migrations/$filename.sql")
        return  file.reader(Charset.defaultCharset()).readText()
    }
}