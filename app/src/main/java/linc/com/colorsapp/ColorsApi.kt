package linc.com.colorsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import rx.Single

interface ColorsApi {

    @GET("category/{color}")
    fun getColorsByCategory(@Path("color") category: String) : Single<String>

    @GET("/")
    fun getColors() : Call<String>

}