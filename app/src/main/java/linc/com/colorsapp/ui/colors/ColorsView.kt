package linc.com.colorsapp.ui.colors

import linc.com.colorsapp.domain.ColorModel

interface ColorsView {
    fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>)
    fun showError(message: String)
}