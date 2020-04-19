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

    override fun saveColor(color: ColorModel): Completable {
        return colorsRepository.saveColor(
            color.apply {
                custom = true
            }
        )
    }

}