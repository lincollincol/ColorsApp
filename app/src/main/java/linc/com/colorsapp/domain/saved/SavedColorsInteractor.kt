package linc.com.colorsapp.domain.saved

import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel

interface SavedColorsInteractor {
    fun execute(): Single<List<ColorModel>>
}