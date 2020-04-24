package linc.com.colorsapp.data.mappers

import linc.com.colorsapp.data.db.ColorRoomEntity
import linc.com.colorsapp.domain.ColorModel
import java.util.*

class ColorModelMapper {

    fun toColorModel(colorRoomEntity: ColorRoomEntity) = ColorModel(
        id = colorRoomEntity.id,
        name = colorRoomEntity.name,
        hex = colorRoomEntity.hex,
        rgb = colorRoomEntity.rgb,
        saved = colorRoomEntity.saved,
        custom = colorRoomEntity.custom
    )

    // TODO: hardcode to const

    fun toColorRoomEntity(colorModel: ColorModel) = ColorRoomEntity(
        id = colorModel.id ?: UUID.randomUUID().toString(),
        name = colorModel.name ?: "White",
        hex = colorModel.hex ?: "#FFFFFF",
        rgb = colorModel.rgb ?: "rgb(255, 255, 255)",
        saved = colorModel.saved!!,
        custom = colorModel.custom!!
    )

}