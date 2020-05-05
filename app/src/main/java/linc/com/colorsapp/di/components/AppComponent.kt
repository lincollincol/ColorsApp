package linc.com.colorsapp.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import linc.com.colorsapp.di.modules.global.NetworkModule
import linc.com.colorsapp.di.modules.global.StorageModule
import linc.com.colorsapp.di.modules.global.UtilsModule
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class, UtilsModule::class])
interface AppComponent {

    fun getMainActivityComponentBuilder(): MainActivitySubComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}