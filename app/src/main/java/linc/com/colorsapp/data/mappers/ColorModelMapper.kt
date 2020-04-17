package linc.com.colorsapp.data.mappers

import android.graphics.Color
import linc.com.colorsapp.data.db.ColorRoomEntity
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.KeyGenerator

class ColorModelMapper {

    fun toColorModel(colorRoomEntity: ColorRoomEntity) = ColorModel(
        name = colorRoomEntity.name,
        hex = colorRoomEntity.hex,
        rgb = colorRoomEntity.rgb
    )

    fun toColorRoomEntity(colorModel: ColorModel) = ColorRoomEntity(
        id = KeyGenerator.generateRandom(),
        name = colorModel.name!!,
        hex = colorModel.hex!!,
        rgb = colorModel.rgb!!
    )

    fun toColorModelsList(colorRoomEntities: List<ColorRoomEntity>) =
        mutableListOf<ColorModel>().apply {
            colorRoomEntities.forEach {
                this.add(toColorModel(it))
            }
        }

    fun toColorEntitiesList(colorModels: List<ColorModel>) =
        mutableListOf<ColorRoomEntity>().apply {
            colorModels.forEach {
                this.add(toColorRoomEntity(it))
            }
        }

}