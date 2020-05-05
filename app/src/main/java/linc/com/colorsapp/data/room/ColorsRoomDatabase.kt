package linc.com.colorsapp.data.room

import android.content.Context
import androidx.room.*
import linc.com.colorsapp.utils.Constants.Companion.DATABASE_NAME


@Database(entities = [ColorRoomEntity::class], version = 1, exportSchema = false)
abstract class ColorsRoomDatabase : RoomDatabase() {
    abstract fun colorsDao(): ColorsDao
}