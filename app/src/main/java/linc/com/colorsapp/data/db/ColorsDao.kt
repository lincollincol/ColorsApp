package linc.com.colorsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ColorsDao {

    @Query("SELECT * FROM colors")
    fun getAll(): List<ColorRoomEntity>

    @Query("SELECT COUNT(*) FROM colors")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(colors: List<ColorRoomEntity>)

}