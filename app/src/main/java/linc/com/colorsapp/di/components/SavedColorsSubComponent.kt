package linc.com.colorsapp.di.components

import dagger.Subcomponent
import linc.com.colorsapp.di.modules.features.ColorsAdapterModule
import linc.com.colorsapp.di.modules.features.data.ColorsDataModule
import linc.com.colorsapp.di.modules.features.domain.ColorsDomainModule
import linc.com.colorsapp.di.modules.features.domain.SavedColorsDomainModule
import linc.com.colorsapp.di.modules.features.presentation.ColorsPresentationModule
import linc.com.colorsapp.di.modules.features.presentation.SavedColorsPresentationModule
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.fragments.OwnColorsFragment
import linc.com.colorsapp.ui.fragments.SavedColorsFragment

@FragmentScope
@Subcomponent(modules = [
    ColorsAdapterModule::class,
    SavedColorsPresentationModule::class,
    SavedColorsDomainModule::class,
    ColorsDataModule::class
])
interface SavedColorsSubComponent {
    fun inject(savedColorsFragment: SavedColorsFragment)
    @Subcomponent.Builder
    interface Builder {
        fun build(): SavedColorsSubComponent
    }
}