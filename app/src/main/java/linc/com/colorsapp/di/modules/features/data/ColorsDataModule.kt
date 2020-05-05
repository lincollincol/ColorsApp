package linc.com.colorsapp.di.modules.features.data

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.ColorsRepository

@Module
interface ColorsDataModule {

    @FragmentScope
    @Binds
    fun bindColorsRepository(colorsRepositoryImpl: ColorsRepositoryImpl) : ColorsRepository

}