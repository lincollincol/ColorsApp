package linc.com.colorsapp.data.db

import android.content.Context
import android.graphics.Color
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import linc.com.colorsapp.utils.Constants.Companion.DATABASE_NAME


@Database(entities = [ColorRoomEntity::class], version = 1, exportSchema = false)
abstract class ColorsRoomDatabase : RoomDatabase() {

    abstract fun colorsDao(): ColorsDao

    companion object {
        @Volatile
        private var INSTANCE: ColorsRoomDatabase? = null

        fun getDatabase(context: Context): ColorsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ColorsRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}