package linc.com.colorsapp.di.components

import dagger.Subcomponent
import linc.com.colorsapp.di.modules.features.ColorsAdapterModule
import linc.com.colorsapp.di.modules.features.data.ColorsDataModule
import linc.com.colorsapp.di.modules.features.domain.ColorsDomainModule
import linc.com.colorsapp.di.modules.features.domain.OwnColorsDomainModule
import linc.com.colorsapp.di.modules.features.presentation.ColorsPresentationModule
import linc.com.colorsapp.di.modules.features.presentation.OwnColorsPresentationModule
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.fragments.NewColorFragment
import linc.com.colorsapp.ui.fragments.OwnColorsFragment

@FragmentScope
@Subcomponent(modules = [
    ColorsAdapterModule::class,
    OwnColorsPresentationModule::class,
    OwnColorsDomainModule::class,
    ColorsDataModule::class
])
interface OwnColorsSubComponent {
    fun inject(ownColorsFragment: OwnColorsFragment)
    @Subcomponent.Builder
    interface Builder {
        fun build(): OwnColorsSubComponent
    }
}