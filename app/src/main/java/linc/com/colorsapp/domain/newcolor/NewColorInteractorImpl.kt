package linc.com.colorsapp.domain.newcolor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository
import java.util.*

class NewColorInteractorImpl(
    private val colorsRepository: ColorsRepository
) : NewColorInteractor {

    override fun saveCustomColor(color: ColorModel): Completable {
        return colorsRepository.saveCustomColor(
            color.apply {
                if(id == null)
                    id = UUID.randomUUID().toString()
                custom = true
            }
        )
    }

}