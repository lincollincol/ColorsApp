package linc.com.colorsapp.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ColorsRepository {

    fun getAllColors(): Single<List<ColorModel>>
    fun getSavedColors(): Single<List<ColorModel>>
    fun getCustomColors(): Single<List<ColorModel>>
    fun saveCustomColor(color: ColorModel): Completable
    fun saveColors(colors: List<ColorModel>): Completable
    fun deleteColors(colors: List<ColorModel>): Completable

}