package linc.com.colorsapp.ui.onwcolors

import linc.com.colorsapp.domain.ColorModel

interface OwnColorsView {

    fun showColors(colors: List<ColorModel>, cardHeights: List<Int>)
    fun showNewColor(color: ColorModel, cardHeight: Int)
    fun showError(message: String)

}