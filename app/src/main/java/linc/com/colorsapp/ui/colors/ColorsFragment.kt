package linc.com.colorsapp.ui.colors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import linc.com.colorsapp.ColorsApp

import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl
import linc.com.colorsapp.ui.NavigatorActivity
import linc.com.colorsapp.ui.details.ColorDetailsFragment
import linc.com.colorsapp.utils.WebPageParser

class ColorsFragment : Fragment(), ColorsView, ColorsAdapter.ColorClickListener {

    private lateinit var colorsAdapter: ColorsAdapter
    private var presenter: ColorsPresenter? = null

    companion object {
        fun newInstance() = ColorsFragment()
    }

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

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsAdapter = ColorsAdapter().apply {
            setOnColorClickListener(this@ColorsFragment)
        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            .apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

        view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = colorsAdapter
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }

        presenter?.getColors()
    }

    override fun showColors(colors: List<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable("COLOR", colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }


}
