package com.example.todounibavekotlin.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.nio.charset.Charset

class Database(var context: Context, private val const: DatabaseConstants = DatabaseConstants())
    : SQLiteOpenHelper(context, const.dbName, null, const.dbVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        onUpgrade(db, 1, const.dbVersion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val numberPattern = "%03d"
        for (i in oldVersion..newVersion) {
            val script = loadStatements(
                "V__${numberPattern.format(i)}"
            )

            script.filter{ s -> !s.isBlank() }.forEach {
                    stmt -> db!!.execSQL(stmt)
            }
        }
    }

    private fun loadStatements(filename: String): List<String> {
        return  loadScript(filename)
                    .replace("(\\/\\*([\\s\\S]*?)\\*\\/)|(--(.)*)|(\\n)|(\\r)", "")
                    .split(";")
    }

    private fun loadScript(filename: String): String {
        val file = context.assets.open("migrations/$filename.sql")
        return  file.reader(Charset.defaultCharset()).readText()
    }
}