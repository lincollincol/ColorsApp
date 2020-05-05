package linc.com.colorsapp.ui.views

import linc.com.colorsapp.domain.ColorModel

interface SavedColorsView {

    fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>)
    fun deleteColor(color: ColorModel)
    fun showError(message: String)

}