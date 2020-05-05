package linc.com.colorsapp.data.room

import androidx.room.*

@Dao
interface ColorsDao {

    @Query("SELECT * FROM colors WHERE custom = 0")
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


    @Update
    fun updateColors(colors: List<ColorRoomEntity>)


    @Delete
    fun deleteColors(colors: List<ColorRoomEntity>)

}