package linc.com.colorsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ColorsDao {

    //
    // Get
    //
    @Query("SELECT * FROM colors")
    fun getAll(): List<ColorRoomEntity>

    @Query("SELECT * FROM colors WHERE saved = 1")
    fun getSaved(): List<ColorRoomEntity>

    @Query("SELECT * FROM colors WHERE custom = 1")
    fun getCustom(): List<ColorRoomEntity>

    @Query("SELECT COUNT(*) FROM colors")
    fun getCount(): Int

    //
    // Insert
    //
    @Insert
    fun insert(color: ColorRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColors(colors: List<ColorRoomEntity>)

    //
    // Update
    //
    @Update
    fun updateColors(colors: List<ColorRoomEntity>)


    //
    // Delete
    //
    @Delete
    fun deleteColors(colors: List<ColorRoomEntity>)

}