package linc.com.colorsapp.ui.presenters.api

import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.views.ColorsView

interface ColorsPresenter : BasePresenter<ColorsView> {
    fun getColors()
    fun saveColors(colors: List<ColorModel>)
}