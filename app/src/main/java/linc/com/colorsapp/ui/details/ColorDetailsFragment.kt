package linc.com.colorsapp.ui.details

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.Constants.Companion.KEY_COLOR

class ColorDetailsFragment : DialogFragment() {

    companion object {
        fun newInstance(bundle: Bundle) = ColorDetailsFragment()
            .apply {
                arguments = bundle
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ColorDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_color_details, container, false)

        val color = arguments?.get(KEY_COLOR) as ColorModel

        view.findViewById<CardView>(R.id.card).apply {
            setCardBackgroundColor(Color.parseColor(color.hex))
        }

        view.findViewById<TextView>(R.id.colorTitle)?.apply {
            text = color.name
            setTextColor(ColorUtil.getReadableColor(Color.parseColor(color.hex)))
        }
        view.findViewById<TextView>(R.id.colorHexCode)?.text = color.hex
        view.findViewById<TextView>(R.id.colorRgbCode)?.text = color.rgb

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        println("POP")
//        (activity as NavigatorActivity).popBack()
        super.onDismiss(dialog)
    }

}