package linc.com.colorsapp.data.repository

import androidx.room.PrimaryKey
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.completable.CompletableCreate
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.db.ColorsDao
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository
import linc.com.colorsapp.utils.WebPageParser

class ColorsRepositoryImpl(
    private val colorsDao: ColorsDao,
    private val colorsApi: ColorsApi,
    private val colorModelMapper: ColorModelMapper,
    private val webPageParser: WebPageParser
) : ColorsRepository {

    override fun getAllColors(): Single<List<ColorModel>> = Single.create {
        // todo make other dep to internet
        if(colorsDao.getCount() == 0) {
            val response = colorsApi.getColors().execute()
            val models = webPageParser.parseHtmlResponse(response.body())
            colorsDao.insertColors(models.map { model ->
                colorModelMapper.toColorRoomEntity(model)
            })
            it.onSuccess(models)
        }else {
            it.onSuccess(colorsDao.getAll().map { entity ->
                colorModelMapper.toColorModel(entity)
            })
        }
    }

    override fun getSavedColors(): Single<List<ColorModel>> {
        return Single.create {
            it.onSuccess(colorsDao.getSaved().map { entity ->
                colorModelMapper.toColorModel(entity)
            })
        }
    }

    override fun getCustomColors(): Single<List<ColorModel>> {
        return Single.create {
            it.onSuccess(colorsDao.getCustom().map { entity ->
                colorModelMapper.toColorModel(entity)
            })
        }
    }

    override fun saveCustomColor(color: ColorModel): Completable {
        return Completable.create {
            colorsDao.insert(colorModelMapper.toColorRoomEntity(color))
            it.onComplete()
        }
    }

    override fun saveColors(colors: List<ColorModel>): Completable {
        return Completable.create {
            colorsDao.insertColors(colors.map { colorModel ->
                colorModelMapper.toColorRoomEntity(colorModel)
            })
            it.onComplete()
        }
    }

    override fun deleteColors(colors: List<ColorModel>): Completable {
        return Completable.create {
            colorsDao.deleteColors(colors.map { colorModel ->
                colorModelMapper.toColorRoomEntity(colorModel)
            })
            it.onComplete()
        }
    }

}