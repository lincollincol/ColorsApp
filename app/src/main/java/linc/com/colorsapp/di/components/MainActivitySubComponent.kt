package linc.com.colorsapp.di.components

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import dagger.BindsInstance
import dagger.Subcomponent
import linc.com.colorsapp.di.modules.activity.NavigatorModule
import linc.com.colorsapp.di.scopes.ActivityScope
import linc.com.colorsapp.ui.activities.MainActivity

@ActivityScope
@Subcomponent(modules = [NavigatorModule::class])
interface MainActivitySubComponent {

    fun inject(activity: MainActivity)

    fun getColorsComponentBuilder(): ColorsSubComponent.Builder
    fun getSavedColorsComponentBuilder(): SavedColorsSubComponent.Builder
    fun getOwnColorsComponentBuilder(): OwnColorsSubComponent.Builder
    fun getNewColorComponentBuilder(): NewColorSubComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fragmentManager(fragmentManager: FragmentManager): Builder

        @BindsInstance
        fun container(@IdRes container: Int): Builder

        fun build(): MainActivitySubComponent
    }

}