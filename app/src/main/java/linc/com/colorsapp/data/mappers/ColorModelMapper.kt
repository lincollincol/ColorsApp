package linc.com.colorsapp.data.mappers

import linc.com.colorsapp.data.room.ColorRoomEntity
import linc.com.colorsapp.domain.ColorModel
import java.util.*

class ColorModelMapper {

    fun toColorModel(colorRoomEntity: ColorRoomEntity) = ColorModel(
        id = colorRoomEntity.id,
        title = colorRoomEntity.name,
        hex = colorRoomEntity.hex,
        rgb = colorRoomEntity.rgb,
        saved = colorRoomEntity.saved,
        custom = colorRoomEntity.custom
    )

    fun toColorRoomEntity(colorModel: ColorModel) = ColorRoomEntity(
        id = colorModel.id!!,
        name = colorModel.title,
        hex = colorModel.hex,
        rgb = colorModel.rgb,
        saved = colorModel.saved,
        custom = colorModel.custom
    )

}