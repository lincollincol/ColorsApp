package linc.com.colorsapp.ui.newcolor

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.dialog_new_color.*
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel


class NewColorFragment : DialogFragment() {

    private lateinit var onSaveListener: OnSaveListener

    companion object {
        fun newInstance() = NewColorFragment()
    }

    interface OnSaveListener {
        fun onSave(colorModel: ColorModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_new_color, container, false)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener{ dismiss() }
        toolbar.setOnMenuItemClickListener {
            onSaveListener.onSave(ColorModel(
                    colorTitle.text.toString(),
                    colorHexCode.text.toString(),
                    colorRgbCode.text.toString()
            ))
            dismiss()
            true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setWindowAnimations(R.style.FullScreenDialogAnimations)
        }
    }

    fun setOnSaveListener(onSaveListener: OnSaveListener) {
        this.onSaveListener = onSaveListener
    }
}