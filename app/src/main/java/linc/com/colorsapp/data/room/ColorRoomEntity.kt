package linc.com.colorsapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import linc.com.colorsapp.utils.Constants.Companion.COLORS_TABLE_NAME

@Entity(tableName = COLORS_TABLE_NAME)
class ColorRoomEntity(
    @PrimaryKey
    val id: String,
    var name: String,
    var hex: String,
    var rgb: String,
    var saved: Boolean,
    var custom: Boolean
)