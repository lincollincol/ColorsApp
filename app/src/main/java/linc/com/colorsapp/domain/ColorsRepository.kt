package linc.com.colorsapp.domain

import io.reactivex.rxjava3.core.Single

interface ColorsRepository {

    fun getColors(): Single<List<ColorModel>>

}