package linc.com.colorsapp.ui.presenters.api

import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.views.SavedColorsView

interface SavedColorsPresenter : BasePresenter<SavedColorsView> {

    fun getColors()
    fun deleteColors(colors: List<ColorModel>)

}