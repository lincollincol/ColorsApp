package linc.com.colorsapp.ui.colors


import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_colors.*
import linc.com.colorsapp.ColorsApp

import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsInteractorImpl
import linc.com.colorsapp.utils.WebPageParser

class ColorsFragment : Fragment(), ColorsView {

    private lateinit var colorsAdapter: ColorsAdapter
    private var presenter: ColorsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(presenter == null) {
            presenter = ColorsPresenter(
                ColorsInteractorImpl(
                    ColorsRepositoryImpl(
                        ColorsApp.colorsDatabase.colorsDao(),
                        ColorsApp.retrofit.create(ColorsApi::class.java),
                        ColorModelMapper(),
                        WebPageParser()
                    )
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()
        presenter?.bind(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        colorsAdapter = ColorsAdapter()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            .apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

        colorsList.apply {
            adapter = colorsAdapter
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }

        presenter?.getColors()
    }

    override fun showColors(colors: List<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setData(colors, cardHeights)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        presenter?.unbind()
    }

}