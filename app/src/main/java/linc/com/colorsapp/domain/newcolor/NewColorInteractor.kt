package linc.com.colorsapp.domain.newcolor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel

interface NewColorInteractor {
    fun saveCustomColor(color: ColorModel): Completable
}