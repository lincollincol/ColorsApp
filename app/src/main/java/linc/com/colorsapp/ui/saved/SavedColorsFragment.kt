package linc.com.colorsapp.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.NavigatorActivity
import linc.com.colorsapp.ui.colors.ColorsAdapter
import linc.com.colorsapp.ui.details.ColorDetailsFragment

class SavedColorsFragment : Fragment(), ColorsAdapter.ColorClickListener {

    private lateinit var colorsAdapter: ColorsAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsAdapter = ColorsAdapter().apply {
            setOnColorClickListener(this@SavedColorsFragment)
            setData(arrayListOf(
                ColorModel("BLACK", "#000000", "rgb(0,0,0)"),
                ColorModel("WHITE", "#ffffff", "rgb(255, 255, 255)")),
                arrayListOf(504, 308)
            )
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
    }

    override fun onClick(colorModel: ColorModel) {
        val data = Bundle().apply {
            putParcelable("COLOR", colorModel)
        }
        (activity as NavigatorActivity)
            .navigateToDialog(ColorDetailsFragment.newInstance(data))
    }

}