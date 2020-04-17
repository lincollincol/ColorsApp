package linc.com.colorsapp.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import rx.Single

interface ColorsApi {

    @GET("/")
    fun getColors() : Call<String>

}