package linc.com.colorsapp.ui.presenters.api

import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.views.NewColorView

interface NewColorPresenter : BasePresenter<NewColorView> {

    fun saveCustomColor(color: ColorModel)
    fun closeWithoutSaving()

}