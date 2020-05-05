package linc.com.colorsapp.di.modules.features

import dagger.Binds
import dagger.Module
import dagger.Provides
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.SelectionManager

@Module
class ColorsAdapterModule {

    @FragmentScope
    @Provides
    fun provideColorsAdapter() = ColorsAdapter()

    @FragmentScope
    @Provides
    fun provideSelectionManager() = SelectionManager<ColorModel>()

}