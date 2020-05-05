package linc.com.colorsapp.ui.presenters.api

import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.views.OwnColorsView

interface OwnColorsPresenter : BasePresenter<OwnColorsView> {

    fun getColors()
    fun createColor()
    fun deleteColors(colors: List<ColorModel>)
    fun editItem(color: ColorModel)

}