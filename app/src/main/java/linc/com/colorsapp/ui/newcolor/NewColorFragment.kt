package linc.com.colorsapp.ui.newcolor

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import androidx.transition.TransitionSet.ORDERING_SEQUENTIAL
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.fragment_new_color.*
import linc.com.colorsapp.ColorsApp
import linc.com.colorsapp.R
import linc.com.colorsapp.data.api.ColorsApi
import linc.com.colorsapp.data.mappers.ColorModelMapper
import linc.com.colorsapp.data.repository.ColorsRepositoryImpl
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.newcolor.NewColorInteractorImpl
import linc.com.colorsapp.ui.activities.BottomMenuActivity
import linc.com.colorsapp.ui.activities.InputActivity
import linc.com.colorsapp.ui.activities.NavigatorActivity
import linc.com.colorsapp.ui.activities.ToolbarActivity
import linc.com.colorsapp.ui.onwcolors.OwnColorsFragment
import linc.com.colorsapp.utils.WebPageParser


class NewColorFragment : Fragment(), NewColorView {

    private var presenter: NewColorPresenter? = null

    companion object {
        fun newInstance() = NewColorFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(presenter == null) {
            presenter = NewColorPresenter(
                NewColorInteractorImpl(
                    ColorsRepositoryImpl(
                        ColorsApp.colorsDatabase.colorsDao(),
                        ColorsApp.retrofit.create(ColorsApi::class.java),
                        ColorModelMapper(),
                        WebPageParser()
                    )
                )
            )
        }

        val slideBottom = Slide(Gravity.BOTTOM).apply {
            addTarget(R.id.newColorLayout)
            addTarget(R.id.appBar)
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        }

        enterTransition = TransitionSet().apply {
            ordering = ORDERING_SEQUENTIAL
            addTransition(slideBottom)
            addTransition(Fade(Fade.IN).apply {
                addTarget(R.id.colorTitle)
                addTarget(R.id.colorPicker)
                addTarget(R.id.colorHexCode)
                addTarget(R.id.colorRgbCode)
                duration = 200
            })
        }

        exitTransition = slideBottom


    }

    override fun onResume() {
        super.onResume()
        presenter?.bind(this)
        (activity as ToolbarActivity).hideToolbar()
        (activity as BottomMenuActivity).hideMenu()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
        (activity as ToolbarActivity).showToolbar()
        (activity as BottomMenuActivity).showMenu()
        (activity as InputActivity).hideKeyboard()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_color, container, false)
    }

    // todo save color (onSaveInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            presenter?.closeWitoutSaving()
        }
        toolbar.setOnMenuItemClickListener {
            presenter?.saveCustomColor(
                ColorModel(
                    name = colorTitle.text.toString(),
                    hex = colorHexCode.text.toString(),
                    rgb = colorRgbCode.text.toString()
                )
            )

            true
        }

        colorPicker.setColorListener(ColorEnvelopeListener { envelope, _ ->
            colorHexCode.text = view.context.getString(
                R.string.title_color_hex,
                envelope.hexCode
            )
            colorRgbCode.text = view.context.getString(
                R.string.title_color_rgb,
                envelope.argb[1],
                envelope.argb[2],
                envelope.argb[3]
            )
            colorTemplate.background
                .setColorFilter(
                    Color.parseColor("#${envelope.hexCode}"),
                    PorterDuff.Mode.SRC_IN
                )
        })

        colorTitle.setText("")
    }

    override fun close() {
        (activity as NavigatorActivity)
            .popBackStack(OwnColorsFragment.newInstance())
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}