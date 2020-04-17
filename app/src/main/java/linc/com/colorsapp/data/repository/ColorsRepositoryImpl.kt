package linc.com.colorsapp.data.repository

import androidx.room.PrimaryKey
import io.reactivex.rxjava3.core.Single
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

    override fun getColors(): Single<List<ColorModel>> = Single.create {
        if(colorsDao.getCount() == 0) {
            val response = colorsApi.getColors().execute()
            val models = webPageParser.parseHtmlResponse(response.body())
            colorsDao.insert(models.map {
                    model -> colorModelMapper.toColorRoomEntity(model)
            })
            it.onSuccess(models)
        }else {
            it.onSuccess(colorsDao.getAll().map {
                    entity -> colorModelMapper.toColorModel(entity)
            })
        }
    }


}