package linc.com.colorsapp.ui.onwcolors


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Slide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import linc.com.colorsapp.ColorsApp

import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractorImpl
import linc.com.colorsapp.ui.activities.NavigatorActivity
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.SelectionManager
import linc.com.colorsapp.ui.custom.SelectionActionMode
import linc.com.colorsapp.ui.details.ColorDetailsFragment
import linc.com.colorsapp.ui.newcolor.NewColorFragment
import linc.com.colorsapp.utils.Constants.Companion.KEY_COLOR
import linc.com.colorsapp.utils.WebPageParser

class OwnColorsFragment : Fragment(),
    OwnColorsView,
    View.OnClickListener,
    ColorsAdapter.ColorClickListener,
    SelectionActionMode.OnActionClickListener {

    private lateinit var colorsAdapter: ColorsAdapter
    private lateinit var selectionManager: SelectionManager<ColorModel>
    private var presenter: OwnColorsPresenter? = null
    private var actionMode: ActionMode? = null

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

        enterTransition = Slide(Gravity.BOTTOM).apply {
            interpolator = LinearOutSlowInInterpolator()
            duration = 500
            addTarget(R.id.newColor)
        }

    }

    override fun onResume() {
        super.onResume()
        println("RESUME")
        presenter?.bind(this)
        presenter?.getColors()
    }

    override fun onStop() {
        super.onStop()
        println("STOP")
        presenter?.unbind()
        actionMode?.finish()
        actionMode = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_own_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectionManager = SelectionManager()
        selectionManager.setSelectionListener(object : SelectionManager.SelectionListener {
            override fun selectionChanged(count: Int) {
                if(actionMode == null) {
                    actionMode = (activity as AppCompatActivity)
                        .startSupportActionMode(SelectionActionMode(
                            activity!!.applicationContext,
                            selectionManager,
                            this@OwnColorsFragment,
                            SelectionActionMode.Type.DELETE)
                        )
                }

                actionMode?.title = view.context.getString(
                    R.string.title_selected_items,
                    count
                )

                if(count == 0) {
                    actionMode?.finish()
                    actionMode= null
                }
            }

            override fun selectionRemoved() {
                actionMode = null
            }
        })

        colorsAdapter = ColorsAdapter().apply {
            setOnColorClickListener(this@OwnColorsFragment)
            setSelectionManager(selectionManager)
        }

        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }

        view.findViewById<FloatingActionButton>(R.id.newColor).setOnClickListener(this)

        view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = AlphaInAnimationAdapter(colorsAdapter).apply {
                setHasFixedSize(true)
                setFirstOnly(false)
            }
            itemAnimator = FadeInAnimator()
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }

    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable(KEY_COLOR, colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }

    override fun onClick(v: View?) {
        (activity as NavigatorActivity)
            .navigateToFragment(NewColorFragment.newInstance())
    }

    override fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
    }

    override fun showNewColor(color: ColorModel, cardHeight: Int) {
        colorsAdapter.insertColor(color, cardHeight)
    }

    override fun deleteColor(color: ColorModel) {
        colorsAdapter.deleteColor(color)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActionClick(item: MenuItem?) {
        presenter?.deleteColors(selectionManager.getSelected().toList())
    }

}