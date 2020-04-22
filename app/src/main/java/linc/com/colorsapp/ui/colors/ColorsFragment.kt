package linc.com.colorsapp.ui.colors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.dialog_new_color.*
import linc.com.colorsapp.ColorsApp

import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl
import linc.com.colorsapp.ui.NavigatorActivity
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.selection.ColorKeyProvider
import linc.com.colorsapp.ui.adapters.selection.ColorLookup
import linc.com.colorsapp.ui.custom.SelectionActionMode
import linc.com.colorsapp.ui.details.ColorDetailsFragment
import linc.com.colorsapp.utils.Constants.Companion.COLOR_ID
import linc.com.colorsapp.utils.Constants.Companion.SELECTION_ID
import linc.com.colorsapp.utils.WebPageParser
import linc.com.colorsapp.utils.isNull
import linc.com.colorsapp.utils.toList

class ColorsFragment : Fragment(), ColorsView, ColorsAdapter.ColorClickListener, SelectionActionMode.OnActionClickListener {

    private lateinit var colorsAdapter: ColorsAdapter
    private lateinit var colorKeyProvider: ColorKeyProvider
    private lateinit var tracker: SelectionTracker<ColorModel>
    private var presenter: ColorsPresenter? = null
    private var actionMode: ActionMode? = null

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
        actionMode?.finish()
        actionMode = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsAdapter = ColorsAdapter()
        colorKeyProvider = ColorKeyProvider()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            .apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

        // todo refactor
        val rv = view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = colorsAdapter
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }

        tracker = SelectionTracker.Builder(
            SELECTION_ID,
            rv,
            colorKeyProvider,
            ColorLookup(rv),
            StorageStrategy.createParcelableStorage(ColorModel::class.java)
        ).build()

        tracker.addObserver(object : SelectionTracker.SelectionObserver<ColorModel>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                actionMode = if(tracker.hasSelection()) {
                    actionMode.isNull {
                        (activity as AppCompatActivity).startSupportActionMode(
                            SelectionActionMode(
                                activity!!.applicationContext,
                                tracker,
                                this@ColorsFragment,
                                SelectionActionMode.Type.SAVE)
                        )
                    }.apply {
                        this?.title = view.context.getString(
                            R.string.title_selected_items,
                            tracker.selection.size()
                        )
                    }
                }else {
                    actionMode?.finish()
                    null
                }

            }
        })

        colorsAdapter.apply {
            setOnColorClickListener(this@ColorsFragment)
            setSelectionTracker(tracker)
        }

        presenter?.getColors()
    }

    override fun showColors(colors: List<ColorModel>, cardHeights: List<Int>) {
        colorsAdapter.setColors(colors, cardHeights)
        colorKeyProvider.setColors(colors)
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable(COLOR_ID, colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }

    override fun onActionClick(item: MenuItem?) {
        presenter?.saveColors(tracker.toList())
    }

}
