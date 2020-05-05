package linc.com.colorsapp.utils

import linc.com.colorsapp.data.room.ColorRoomEntity
import linc.com.colorsapp.data.utilsapi.ColorModelMapper
import linc.com.colorsapp.domain.ColorModel
import java.util.*
import javax.inject.Inject

class ColorModelMapperImpl @Inject constructor() : ColorModelMapper {

    override fun toColorModel(colorRoomEntity: ColorRoomEntity) = ColorModel(
        id = colorRoomEntity.id,
        title = colorRoomEntity.name,
        hex = colorRoomEntity.hex,
        rgb = colorRoomEntity.rgb,
        saved = colorRoomEntity.saved,
        custom = colorRoomEntity.custom
    )

    override fun toColorRoomEntity(colorModel: ColorModel) = ColorRoomEntity(
        id = colorModel.id!!,
        name = colorModel.title,
        hex = colorModel.hex,
        rgb = colorModel.rgb,
        saved = colorModel.saved,
        custom = colorModel.custom
    )

}