package linc.com.colorsapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import androidx.transition.TransitionSet.ORDERING_SEQUENTIAL
import kotlinx.android.synthetic.main.fragment_new_color.*
import linc.com.colorsapp.R
import linc.com.colorsapp.di.components.NewColorSubComponent
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.activities.types.*
import linc.com.colorsapp.ui.presenters.api.NewColorPresenter
import linc.com.colorsapp.ui.presenters.implementation.NewColorPresenterImpl
import linc.com.colorsapp.ui.views.NewColorView
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.Constants.Companion.KEY_COLOR_MODEL
import javax.inject.Inject


class NewColorFragment : Fragment(), NewColorView {

    @Inject lateinit var presenter: NewColorPresenter
    private var fragmentComponent: NewColorSubComponent? = null
    private var editedColor: ColorModel? = null

    companion object {
        fun newInstance(bundle: Bundle) = NewColorFragment()
            .apply {
            arguments = bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent = (activity as DaggerActivity).getActivitySubComponent()
            .getNewColorComponentBuilder()
            .build()
            .apply {
                inject(this@NewColorFragment)
            }

        super.onCreate(savedInstanceState)

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
                duration = 500
            })
        }

        exitTransition = slideBottom

        if(savedInstanceState != null) {
            editedColor = savedInstanceState.getParcelable(KEY_COLOR_MODEL)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.bind(this)
        (activity as ToolbarActivity).hideToolbar()
        (activity as BottomMenuActivity).hideMenu()
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            presenter.closeWithoutSaving()
        }
        toolbar.setOnMenuItemClickListener {
            presenter.saveCustomColor(
                editedColor!!.apply {
                    title = colorTitle.text.toString()
                }
            )
            true
        }

        colorPicker.subscribe { color, _, _ ->
            colorHexCode.text = view.context.getString(
                R.string.title_color_hex,
                ColorUtil.getHexFromInt(colorPicker.color)
            )
            colorRgbCode.text = view.context.getString(
                R.string.title_color_rgb,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
            )
            editedColor?.apply {
                hex = ColorUtil.getHexFromInt(colorPicker.color)
                rgb = ColorUtil.getRgbFromInt(colorPicker.color)
            }

            scrollView.requestDisallowInterceptTouchEvent(true)
        }

        initColor(arguments?.getParcelable(KEY_COLOR_MODEL))
        arguments?.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_COLOR_MODEL, editedColor)
    }

    override fun close() {
        (activity as NavigatorActivity)
            .popBackStack()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initColor(color: ColorModel?) {
        if(color == null) {
            colorPicker.setInitialColor(Color.parseColor(editedColor?.hex))
            return
        }
        colorTitle.setText(color.title)
        colorHexCode.text = color.hex
        colorRgbCode.text = color.rgb
        colorPicker.setInitialColor(Color.parseColor(color.hex))
        editedColor = color.copy()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentComponent = null
    }

}