package linc.com.colorsapp

import android.app.Application
import android.graphics.Color
import androidx.room.RoomDatabase
import linc.com.colorsapp.data.db.ColorsRoomDatabase
import linc.com.colorsapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ColorsApp : Application() {

    companion object {
        lateinit var instance: ColorsApp
        lateinit var colorsDatabase: ColorsRoomDatabase
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        colorsDatabase = ColorsRoomDatabase.getDatabase(this)
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

}