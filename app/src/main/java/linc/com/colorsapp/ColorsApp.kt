package linc.com.colorsapp

import android.app.Application
import linc.com.colorsapp.di.components.AppComponent
import linc.com.colorsapp.di.components.DaggerAppComponent

class ColorsApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }

}