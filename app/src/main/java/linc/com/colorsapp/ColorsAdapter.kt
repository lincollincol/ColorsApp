package linc.com.colorsapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    private val colorModels = mutableListOf<ColorModel>()
    private val cardHeights = mutableListOf<Int>()

    private var lastAnimatedPosition = -1

    fun setData(colorModels: List<ColorModel>, cardHeights: List<Int>) {
        this.colorModels.updateAll(colorModels)
        this.cardHeights.updateAll(cardHeights)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.color_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return colorModels.count()
    }


    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorModels[position], cardHeights[position])


    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val card = itemView.findViewById<CardView>(R.id.card)
        fun bind(colorModel: ColorModel, cardHeight: Int) {
            val color = Color.parseColor(colorModel.hex)
            title.apply {
                text = colorModel.name
                setTextColor(getReadableColor(color))
            }
            card.apply {
                setCardBackgroundColor(color)
                minimumHeight = cardHeight
            }

        }

        private fun getReadableColor(color: Int): Int {
            val luminance = (0.2126 * Color.red(color)
                    + 0.7152 * Color.green(color)
                    + 0.0722 * Color.blue(color)).toFloat()
            return if (luminance < 140) Color.WHITE else Color.BLACK
        }
    }

}