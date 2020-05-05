package linc.com.colorsapp.di.modules.global

import dagger.Binds
import dagger.Module
import dagger.Provides
import linc.com.colorsapp.ColorsApp
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.utilsapi.WebPageParser
import linc.com.colorsapp.utils.Constants
import linc.com.colorsapp.utils.WebPageParserImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideColorsApi(retrofit: Retrofit): ColorsApi =
        retrofit.create(ColorsApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: ScalarsConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()


    @Singleton
    @Provides
    fun provideConverterFactory(): ScalarsConverterFactory =
        ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

}