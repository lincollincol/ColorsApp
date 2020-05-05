package linc.com.colorsapp.domain.colors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel

interface ColorsInteractor {

    fun execute(): Single<List<ColorModel>>
    fun saveColors(colors: List<ColorModel>): Completable

}