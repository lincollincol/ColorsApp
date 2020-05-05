package linc.com.colorsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import linc.com.colorsapp.R
import linc.com.colorsapp.di.components.NewColorSubComponent
import linc.com.colorsapp.di.components.SavedColorsSubComponent
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.activities.types.DaggerActivity
import linc.com.colorsapp.ui.activities.types.NavigatorActivity
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.SelectionManager
import linc.com.colorsapp.ui.custom.SelectionActionMode
import linc.com.colorsapp.ui.dialogs.ColorDetailsFragmentDialog
import linc.com.colorsapp.ui.presenters.api.SavedColorsPresenter
import linc.com.colorsapp.ui.presenters.implementation.SavedColorsPresenterImpl
import linc.com.colorsapp.ui.views.SavedColorsView
import linc.com.colorsapp.utils.Constants
import linc.com.colorsapp.utils.Constants.Companion.EMPTY_SELECTION
import javax.inject.Inject

class SavedColorsFragment : Fragment(),
    SavedColorsView,
    ColorsAdapter.ColorClickListener,
    SelectionActionMode.OnActionClickListener  {

    @Inject lateinit var colorsAdapter: ColorsAdapter
    @Inject lateinit var selectionManager: SelectionManager<ColorModel>
    @Inject lateinit var presenter: SavedColorsPresenter
    private var fragmentComponent: SavedColorsSubComponent? = null

    private var actionMode: ActionMode? = null

    companion object {
        fun newInstance() = SavedColorsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent = (activity as DaggerActivity).getActivitySubComponent()
            .getSavedColorsComponentBuilder()
            .build()
            .apply {
                inject(this@SavedColorsFragment)
            }

        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
        actionMode?.finish()
        actionMode = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectionManager.setSelectionListener(object : SelectionManager.SelectionListener {
            override fun selectionChanged(count: Int) {
                if(actionMode == null) {
                    actionMode = (activity as AppCompatActivity)
                        .startSupportActionMode(SelectionActionMode(
                            activity!!.applicationContext,
                            selectionManager,
                            this@SavedColorsFragment,
                            SelectionActionMode.Type.DELETE)
                        )
                }

                actionMode?.title = view.context.getString(
                    R.string.title_selected_items,
                    count
                )

                if(count == EMPTY_SELECTION) {
                    actionMode?.finish()
                    actionMode= null
                }
            }

            override fun selectionRemoved() {
                actionMode = null
            }
        })

        colorsAdapter.apply {
            setOnColorClickListener(this@SavedColorsFragment)
            setSelectionManager(selectionManager)
        }

        view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = AlphaInAnimationAdapter(colorsAdapter).apply {
                setHasFixedSize(true)
                setFirstOnly(false)
            }
            itemAnimator = FadeInAnimator()
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        }
        presenter.getColors()

    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable(Constants.KEY_COLOR_MODEL, colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragmentDialog.newInstance(data))
    }

    override fun onActionClick(item: MenuItem?) {
        presenter.deleteColors(selectionManager.getSelected().toList())
    }

    override fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
    }

    override fun deleteColor(color: ColorModel) {
        colorsAdapter.deleteColor(color)
    }

    override fun showError(message: String) {
        Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentComponent = null
    }

}
