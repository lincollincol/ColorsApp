package linc.com.colorsapp.domain.owncolors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel

interface OwnColorsInteractor {

    fun execute(): Single<List<ColorModel>>
    fun saveColor(color: ColorModel): Completable

}