package linc.com.colorsapp.di.components

import dagger.Subcomponent
import linc.com.colorsapp.di.modules.features.ColorsAdapterModule
import linc.com.colorsapp.di.modules.features.data.ColorsDataModule
import linc.com.colorsapp.di.modules.features.domain.ColorsDomainModule
import linc.com.colorsapp.di.modules.features.presentation.ColorsPresentationModule
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.fragments.ColorsFragment

@FragmentScope
@Subcomponent(modules = [
    ColorsAdapterModule::class,
    ColorsPresentationModule::class,
    ColorsDomainModule::class,
    ColorsDataModule::class
])
interface ColorsSubComponent {
    fun inject(colorsFragment: ColorsFragment)
    @Subcomponent.Builder
    interface Builder {
        fun build(): ColorsSubComponent
    }
}