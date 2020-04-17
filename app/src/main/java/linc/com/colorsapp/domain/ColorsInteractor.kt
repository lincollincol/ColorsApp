package linc.com.colorsapp.domain

import io.reactivex.rxjava3.core.Single

interface ColorsInteractor {

    fun execute(): Single<List<ColorModel>>

}