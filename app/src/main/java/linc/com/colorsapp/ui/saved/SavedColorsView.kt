package linc.com.colorsapp.ui.saved

import linc.com.colorsapp.domain.ColorModel

interface SavedColorsView {

    fun showColors(colors: List<ColorModel>, cardHeights: List<Int>)
    fun deleteColor(color: ColorModel)
    fun showError(message: String)

}