package linc.com.colorsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ColorsDao {

    // TODO: 1/0 to const

    @Query("SELECT * FROM colors")
    fun getAll(): List<ColorRoomEntity>

    @Query("SELECT * FROM colors WHERE saved = 1")
    fun getSaved(): List<ColorRoomEntity>

    @Query("SELECT * FROM colors WHERE custom = 1")
    fun getCustom(): List<ColorRoomEntity>

    @Query("SELECT COUNT(*) FROM colors")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(color: ColorRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColors(colors: List<ColorRoomEntity>)

    @Delete
    fun deleteColors(colors: List<ColorRoomEntity>)

}