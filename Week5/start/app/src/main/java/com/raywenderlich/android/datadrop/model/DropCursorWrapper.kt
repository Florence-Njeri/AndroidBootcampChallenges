package com.raywenderlich.android.datadrop.model

import android.database.Cursor
import android.database.CursorWrapper
import com.google.android.gms.maps.model.LatLng

class DropCursorWrapper(cursor:Cursor): CursorWrapper(cursor) {
    //Read data from the SQLITE database
    fun getDrop(): Drop{
        val id = getString(getColumnIndex(DropDbSchema.DropTable.Columns.ID))
        val latitude = getDouble(getColumnIndex(DropDbSchema.DropTable.Columns.LATITUDE))
        val longitude = getDouble(getColumnIndex(DropDbSchema.DropTable.Columns.LONGITUDE))
        val dropMessage = getString(getColumnIndex(DropDbSchema.DropTable.Columns.DROP_MESSAGE))

        return Drop(LatLng(latitude,longitude), dropMessage, id)
    }
}