package linc.com.colorsapp.ui.onwcolors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import linc.com.colorsapp.ColorsApp

import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractorImpl
import linc.com.colorsapp.ui.NavigatorActivity
import linc.com.colorsapp.ui.colors.ColorsAdapter
import linc.com.colorsapp.ui.details.ColorDetailsFragment
import linc.com.colorsapp.ui.newcolor.NewColorFragment
import linc.com.colorsapp.utils.WebPageParser

class OwnColorsFragment : Fragment(), OwnColorsView, View.OnClickListener, ColorsAdapter.ColorClickListener, NewColorFragment.OnSaveListener {

    private lateinit var colorsAdapter: ColorsAdapter
    private var presenter: OwnColorsPresenter? = null

    companion object {
        fun newInstance() = OwnColorsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(presenter == null) {
            presenter = OwnColorsPresenter(
                OwnColorsInteractorImpl(
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
        return inflater.inflate(R.layout.fragment_own_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsAdapter = ColorsAdapter().apply {
            setOnColorClickListener(this@OwnColorsFragment)
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

        view.findViewById<FloatingActionButton>(R.id.newColor).setOnClickListener(this)


        presenter?.getColors()
    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable("COLOR", colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }

    override fun onClick(v: View?) {
        (activity as NavigatorActivity)
            .navigateToDialog(NewColorFragment.newInstance()
                .apply {
                    setOnSaveListener(this@OwnColorsFragment)
                }
            )
    }

    override fun onSave(colorModel: ColorModel) {
        println(colorModel.toString())
        presenter?.saveColor(colorModel)
    }

    override fun showColors(colors: List<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
    }

    override fun showNewColor(color: ColorModel, cardHeight: Int) {
        colorsAdapter.insertColor(color, cardHeight)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}