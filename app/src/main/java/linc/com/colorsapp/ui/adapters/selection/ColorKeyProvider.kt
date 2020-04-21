package linc.com.colorsapp.ui.adapters.selection

import androidx.recyclerview.selection.ItemKeyProvider
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.updateAll

class ColorKeyProvider : ItemKeyProvider<ColorModel>(SCOPE_CACHED) {

    private val colors = mutableListOf<ColorModel>()

    override fun getKey(position: Int) = colors[position]
    override fun getPosition(key: ColorModel) = colors.indexOf(key)

    fun setColors(colors: List<ColorModel>) {
        this.colors.updateAll(colors)
    }
}