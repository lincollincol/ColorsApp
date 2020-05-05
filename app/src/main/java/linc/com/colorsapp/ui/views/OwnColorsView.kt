package linc.com.colorsapp.ui.views

import linc.com.colorsapp.domain.ColorModel

interface OwnColorsView {

    fun openEditor(color: ColorModel)
    fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>)
    fun showNewColor(color: ColorModel, cardHeight: Int)
    fun deleteColor(color: ColorModel)
    fun showError(message: String)

}