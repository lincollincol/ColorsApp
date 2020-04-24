package linc.com.colorsapp.domain.owncolors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository

class OwnColorsInteractorImpl(
    private val colorsRepository: ColorsRepository
) : OwnColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getCustomColors()
    }

    override fun saveCustomColor(color: ColorModel): Single<ColorModel> {
        return colorsRepository.saveCustomColor(
            color.apply {
                custom = true
            }
        )
    }

    override fun deleteColors(colors: List<ColorModel>): Completable {
        return colorsRepository.updateColors(colors)
    }

}