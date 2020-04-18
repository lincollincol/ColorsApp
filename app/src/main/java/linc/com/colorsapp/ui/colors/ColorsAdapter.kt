package linc.com.colorsapp.ui.colors

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.R
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.updateAll


class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    private val colorModels = mutableListOf<ColorModel>()
    private val cardHeights = mutableListOf<Int>()
    private lateinit var colorClickListener: ColorClickListener

    fun setData(colorModels: List<ColorModel>, cardHeights: List<Int>) {
        this.colorModels.updateAll(colorModels)
        this.cardHeights.updateAll(cardHeights)
        notifyDataSetChanged()
    }

    fun setOnColorClickListener(colorClickListener: ColorClickListener) {
        this.colorClickListener = colorClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.color_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return colorModels.count()
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorModels[position], cardHeights[position])
    }

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val card = itemView.findViewById<CardView>(R.id.card)
        private val title = itemView.findViewById<TextView>(R.id.title)

        fun bind(colorModel: ColorModel, cardHeight: Int) {
            title.apply {
                text = colorModel.name
                setTextColor(ColorUtil.getReadableColor(Color.parseColor(colorModel.hex)))
            }

            card.apply {
                setCardBackgroundColor(Color.parseColor(colorModel.hex))
                minimumHeight = cardHeight
            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            colorClickListener.onClick(colorModels[adapterPosition])
        }
    }

    interface ColorClickListener {
        fun onClick(colorModel: ColorModel)
    }

}