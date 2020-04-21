package linc.com.colorsapp.ui.adapters.selection

import androidx.recyclerview.selection.ItemDetailsLookup
import linc.com.colorsapp.domain.ColorModel

class ColorDetails(
    private val adapterPosition: Int,
    private val selectedKey: ColorModel?
) : ItemDetailsLookup.ItemDetails<ColorModel>() {

    override fun getSelectionKey() = selectedKey
    override fun getPosition() = adapterPosition

}