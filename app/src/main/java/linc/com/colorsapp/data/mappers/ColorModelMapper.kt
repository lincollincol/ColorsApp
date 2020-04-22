package linc.com.colorsapp.data.mappers

import android.graphics.Color
import linc.com.colorsapp.data.db.ColorRoomEntity
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.KeyGenerator

class ColorModelMapper {

    fun toColorModel(colorRoomEntity: ColorRoomEntity) = ColorModel(
        name = colorRoomEntity.name,
        hex = colorRoomEntity.hex,
        rgb = colorRoomEntity.rgb,
        saved = colorRoomEntity.saved,
        custom = colorRoomEntity.custom
    )

    // TODO: hardcode to const

    fun toColorRoomEntity(colorModel: ColorModel) = ColorRoomEntity(
        id = KeyGenerator.generateRandom(),
        name = colorModel.name ?: "White",
        hex = colorModel.hex ?: "#FFFFFF",
        rgb = colorModel.rgb ?: "rgb(255, 255, 255)",
        saved = colorModel.saved!!,
        custom = colorModel.custom!!
    )

}