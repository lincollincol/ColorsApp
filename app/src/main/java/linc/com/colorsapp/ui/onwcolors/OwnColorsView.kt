package linc.com.colorsapp.ui.onwcolors

import linc.com.colorsapp.domain.ColorModel

interface OwnColorsView {

    fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>)
    fun showNewColor(color: ColorModel, cardHeight: Int)
    fun deleteColor(color: ColorModel)
    fun showError(message: String)

}