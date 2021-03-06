package linc.com.colorsapp.domain.owncolors

import android.graphics.Color
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel

interface OwnColorsInteractor {

    fun execute(): Single<List<ColorModel>>
    fun deleteColors(colors: List<ColorModel>): Completable

}