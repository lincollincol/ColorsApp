package linc.com.colorsapp.data.utilsapi

import linc.com.colorsapp.data.room.ColorRoomEntity
import linc.com.colorsapp.domain.ColorModel

interface ColorModelMapper {

    fun toColorModel(colorRoomEntity: ColorRoomEntity): ColorModel
    fun toColorRoomEntity(colorModel: ColorModel): ColorRoomEntity

}