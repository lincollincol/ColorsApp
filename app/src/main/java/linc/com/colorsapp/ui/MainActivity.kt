package linc.com.colorsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_colors.*
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.R
import linc.com.colorsapp.ui.colors.ColorsAdapter
import linc.com.colorsapp.ui.colors.ColorsFragment
import linc.com.colorsapp.utils.Constants.Companion.BASE_URL
import linc.com.colorsapp.utils.WebPageParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ColorsFragment())
            .commit()
    }


}
