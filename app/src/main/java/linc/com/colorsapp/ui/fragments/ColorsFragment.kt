package linc.com.colorsapp.ui.fragments


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import linc.com.colorsapp.R
import linc.com.colorsapp.di.components.ColorsSubComponent
import linc.com.colorsapp.di.components.SavedColorsSubComponent
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.activities.types.DaggerActivity
import linc.com.colorsapp.ui.activities.types.NavigatorActivity
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.SelectionManager
import linc.com.colorsapp.ui.custom.SelectionActionMode
import linc.com.colorsapp.ui.dialogs.ColorDetailsFragmentDialog
import linc.com.colorsapp.ui.presenters.api.ColorsPresenter
import linc.com.colorsapp.ui.presenters.implementation.ColorsPresenterImpl
import linc.com.colorsapp.ui.views.ColorsView
import linc.com.colorsapp.utils.Constants.Companion.EMPTY_SELECTION
import linc.com.colorsapp.utils.Constants.Companion.KEY_COLOR_MODEL
import javax.inject.Inject


class ColorsFragment : Fragment(),
    ColorsView,
    ColorsAdapter.ColorClickListener,
    SelectionActionMode.OnActionClickListener {

    @Inject lateinit var colorsAdapter: ColorsAdapter
    @Inject lateinit var selectionManager: SelectionManager<ColorModel>
    @Inject lateinit var presenter: ColorsPresenter
    private var fragmentComponent: ColorsSubComponent? = null

    private var actionMode: ActionMode? = null

    companion object {
        fun newInstance() = ColorsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent = (activity as DaggerActivity).getActivitySubComponent()
            .getColorsComponentBuilder()
            .build()
            .apply {
                inject(this@ColorsFragment)
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
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    lateinit var rv: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectionManager.setSelectionListener(object : SelectionManager.SelectionListener {
            override fun selectionChanged(count: Int) {
                if(actionMode == null) {
                    actionMode = (activity as AppCompatActivity)
                        .startSupportActionMode(SelectionActionMode(
                            activity!!.applicationContext,
                            selectionManager,
                            this@ColorsFragment,
                            SelectionActionMode.Type.SAVE)
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
            setOnColorClickListener(this@ColorsFragment)
            setSelectionManager(selectionManager)
        }

        rv = view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = AlphaInAnimationAdapter(colorsAdapter).apply {
                setHasFixedSize(true)
                setFirstOnly(false)
            }
            itemAnimator = FadeInAnimator()
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
            setHasFixedSize(true)
        }

        presenter.getColors()
    }

    override fun showColors(colors: MutableList<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable(KEY_COLOR_MODEL, colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragmentDialog.newInstance(data))
    }

    override fun onActionClick(item: MenuItem?) {
        presenter.saveColors(selectionManager.getSelected().toList())
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentComponent = null
    }

}
