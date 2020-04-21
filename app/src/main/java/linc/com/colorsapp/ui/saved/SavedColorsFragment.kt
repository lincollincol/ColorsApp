package linc.com.colorsapp.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.NavigatorActivity
import linc.com.colorsapp.ui.adapters.ColorsAdapter
import linc.com.colorsapp.ui.adapters.selection.ColorKeyProvider
import linc.com.colorsapp.ui.adapters.selection.ColorLookup
import linc.com.colorsapp.ui.custom.SelectionActionMode
import linc.com.colorsapp.ui.details.ColorDetailsFragment
import linc.com.colorsapp.utils.Constants
import linc.com.colorsapp.utils.isNull

class SavedColorsFragment : Fragment(), ColorsAdapter.ColorClickListener {

    private lateinit var colorsAdapter: ColorsAdapter
    private lateinit var colorKeyProvider: ColorKeyProvider
    private var actionMode: ActionMode? = null

    companion object {
        fun newInstance() = SavedColorsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_colors, container, false)
    }


    override fun onStop() {
        super.onStop()
        actionMode?.finish()
        actionMode = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo refactor
        colorKeyProvider = ColorKeyProvider()
            .apply {
                setColors(arrayListOf(
                    ColorModel("BLACK", "#000000", "rgb(0,0,0)"),
                    ColorModel("WHITE", "#ffffff", "rgb(255, 255, 255)"))
                )
            }

        colorsAdapter = ColorsAdapter()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            .apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

        val rv = view.findViewById<RecyclerView>(R.id.colorsList).apply {
            adapter = colorsAdapter
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
        }


        val tracker = SelectionTracker.Builder<ColorModel>(
            Constants.SELECTION_ID,
            rv,
            colorKeyProvider,
            ColorLookup(rv),
            StorageStrategy.createParcelableStorage(ColorModel::class.java)
        ).build()

        tracker.addObserver(object : SelectionTracker.SelectionObserver<ColorModel>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                actionMode = when(tracker.hasSelection()) {
                    true -> {
                        actionMode.isNull {
                            (activity as AppCompatActivity)
                                .startSupportActionMode(
                                    SelectionActionMode<ColorModel>(
                                    activity!!.applicationContext,
                                    tracker,
                                    SelectionActionMode.Type.DELETE)
                                )
                        }
                    }

                    false -> {
                        actionMode?.finish()
                        null
                    }
                }

            }
        })

        colorsAdapter.apply {
            setSelectionTracker(tracker)
            setOnColorClickListener(this@SavedColorsFragment)
            setColors(arrayListOf(
                ColorModel("BLACK", "#000000", "rgb(0,0,0)"),
                ColorModel("WHITE", "#ffffff", "rgb(255, 255, 255)")),
                arrayListOf(504, 308)
            )
        }

    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable("COLOR", colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }

}
