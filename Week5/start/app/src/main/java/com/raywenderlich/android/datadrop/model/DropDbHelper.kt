package com.raywenderlich.android.datadrop.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DropDbHelper(context: Context) : SQLiteOpenHelper(context, DropDbSchema.DB_NAME, null, DropDbSchema.VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " +
                DropDbSchema.DropTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                DropDbSchema.DropTable.Columns.ID + " text, " +
                DropDbSchema.DropTable.Columns.LONGITUDE + " real, " +
                DropDbSchema.DropTable.Columns.LATITUDE + " real, " +
                DropDbSchema.DropTable.Columns.DROP_MESSAGE + " text" + ");")
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }
}