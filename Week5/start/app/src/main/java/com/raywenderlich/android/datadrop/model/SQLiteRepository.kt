package com.raywenderlich.android.datadrop.model

import android.content.ContentValues
import android.util.Log
import com.raywenderlich.android.datadrop.app.DataDropApplication
import com.raywenderlich.android.datadrop.model.DropDbSchema.DropTable
import java.io.IOException

class SQLiteRepository : DropRepository {
    val database = DropDbHelper(DataDropApplication.getAppContext()).writableDatabase
    override fun addDrop(drop: Drop) {
        val contentValues = getDropContentValues(drop)
        database.insert(DropTable.NAME, null, contentValues)

    }

    override fun getDrops(): List<Drop> {
        val drops = mutableListOf<Drop>()
        val cursor = queryDrops(null, null)
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                drops.add(cursor.getDrop())
                cursor.moveToNext()
            }
        } catch (e: IOException) {
            Log.e("SQLiteRepository", "Error reading drops")
        }
        finally {
            cursor.close()
        }
        return drops
    }

    override fun clearDrop(drop: Drop) {

    }

    override fun clearAllDrops() {

    }

    //Convert drop to a contentValue object

    private fun getDropContentValues(drop: Drop): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DropTable.Columns.ID, drop.id)
        contentValues.put(DropTable.Columns.LATITUDE, drop.latLng.latitude)
        contentValues.put(DropTable.Columns.LONGITUDE, drop.latLng.longitude)
        contentValues.put(DropTable.Columns.DROP_MESSAGE, drop.dropMessage)
        return contentValues
    }

    private fun queryDrops(where: String?, whereArgs: Array<String>?): DropCursorWrapper {
        //Query the database
        val cursor = database.query(
                DropTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null

        )
        return DropCursorWrapper(cursor)
    }
}