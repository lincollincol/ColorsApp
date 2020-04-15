package linc.com.colorsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.math.sign
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://colours.neilorangepeel.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // todo network part
        val parser = WebPageParser()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val colorsApi = retrofit.create(ColorsApi::class.java)

        val colorsAdapter = ColorsAdapter()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            .apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        colorsList.apply {
            adapter = colorsAdapter
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }




        colorsApi.getColors()
            .enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        val colors = parser.parseHtmlResponse(response?.body()!!)
                        val cardHeights = mutableListOf<Int>()
                            .apply {
                                for(i in 0..colors.size) {
                                    add(Random.nextInt(400, 700))
                                }
                            }
                        colorsAdapter.setData(colors, cardHeights)
                    }

                }
            )

    }


}
