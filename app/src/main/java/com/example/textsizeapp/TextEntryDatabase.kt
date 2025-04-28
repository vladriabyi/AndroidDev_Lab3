package com.example.textsizeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TextEntryDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "text_entries.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "entries"
        const val COLUMN_ID = "id"
        const val COLUMN_TEXT = "text"
        const val COLUMN_SIZE = "size"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TEXT TEXT NOT NULL,
                $COLUMN_SIZE INTEGER NOT NULL,
                $COLUMN_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    suspend fun addEntry(text: String, size: Int): Long = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(COLUMN_TEXT, text)
            put(COLUMN_SIZE, size)
        }
        writableDatabase.insert(TABLE_NAME, null, values)
    }

    suspend fun getAllEntries(): List<TextEntry> = withContext(Dispatchers.IO) {
        val entries = mutableListOf<TextEntry>()
        val cursor = readableDatabase.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_TEXT, COLUMN_SIZE, COLUMN_TIMESTAMP),
            null,
            null,
            null,
            null,
            "$COLUMN_TIMESTAMP DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                entries.add(
                    TextEntry(
                        id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID)),
                        text = it.getString(it.getColumnIndexOrThrow(COLUMN_TEXT)),
                        size = it.getInt(it.getColumnIndexOrThrow(COLUMN_SIZE)),
                        timestamp = it.getString(it.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                    )
                )
            }
        }
        entries
    }

    suspend fun clearDatabase(): Boolean = withContext(Dispatchers.IO) {
        try {
            writableDatabase.delete(TABLE_NAME, null, null)
            true
        } catch (e: Exception) {
            false
        }
    }
}

data class TextEntry(
    val id: Long,
    val text: String,
    val size: Int,
    val timestamp: String
) 