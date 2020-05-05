package linc.com.colorsapp.di.components

import dagger.Subcomponent
import linc.com.colorsapp.di.modules.features.ColorsAdapterModule
import linc.com.colorsapp.di.modules.features.data.ColorsDataModule
import linc.com.colorsapp.di.modules.features.domain.ColorsDomainModule
import linc.com.colorsapp.di.modules.features.domain.NewColorDomainModule
import linc.com.colorsapp.di.modules.features.presentation.ColorsPresentationModule
import linc.com.colorsapp.di.modules.features.presentation.NewColorPresentationModule
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.fragments.ColorsFragment
import linc.com.colorsapp.ui.fragments.NewColorFragment

@FragmentScope
@Subcomponent(modules = [
    NewColorPresentationModule::class,
    NewColorDomainModule::class,
    ColorsDataModule::class
])
interface NewColorSubComponent {
    fun inject(newColorFragment: NewColorFragment)
    @Subcomponent.Builder
    interface Builder {
        fun build(): NewColorSubComponent
    }
}