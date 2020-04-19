package linc.com.colorsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
class ColorRoomEntity(
    @PrimaryKey
    val id: String,
    var name: String,
    var hex: String,
    var rgb: String,
    var saved: Boolean,
    var custom: Boolean
)