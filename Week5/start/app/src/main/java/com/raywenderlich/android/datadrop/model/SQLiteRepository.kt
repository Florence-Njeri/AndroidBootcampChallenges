package com.raywenderlich.android.datadrop.model

import android.content.ContentValues
import com.raywenderlich.android.datadrop.app.DataDropApplication
import com.raywenderlich.android.datadrop.model.DropDbSchema.DropTable

class SQLiteRepository : DropRepository {
    val database = DropDbHelper(DataDropApplication.getAppContext()).writableDatabase
    override fun addDrop(drop: Drop) {
        val contentValues = getDropContentValues(drop)
        database.insert(DropTable.NAME, null, contentValues)

    }

    override fun getDrops(): List<Drop> {
        return emptyList()
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
}