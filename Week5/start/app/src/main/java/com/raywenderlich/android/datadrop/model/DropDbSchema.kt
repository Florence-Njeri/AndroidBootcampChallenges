package com.raywenderlich.android.datadrop.model

object DropDbSchema {
    const val VERSION = 1
    const val DB_NAME = "drops.db"

    //Add drop table object {drop table name }

    object DropTable {
        const val NAME = "drops"

        //Add drop table object {drop table column names }
        object Columns {
            const val ID = "id"
            const val LONGITUDE = "longitude"
            const val LATITUDE = "latitude"
            const val DROP_MESSAGE = "dropMessage"
        }
    }


}