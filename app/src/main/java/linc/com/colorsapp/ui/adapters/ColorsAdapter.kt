package linc.com.colorsapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.R
import linc.com.colorsapp.ui.adapters.selection.ColorDetails
import linc.com.colorsapp.ui.adapters.selection.ViewHolderWithDetails
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.updateAll


class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    private val colorModels = mutableListOf<ColorModel>()
    private val cardHeights = mutableListOf<Int>()
    private lateinit var colorClickListener: ColorClickListener
    private lateinit var selectionTracker: SelectionTracker<ColorModel>

    fun setColors(colorModels: List<ColorModel>, cardHeights: List<Int>) {
        this.colorModels.updateAll(colorModels)
        this.cardHeights.updateAll(cardHeights)
        notifyDataSetChanged()
    }

    fun insertColor(colorModel: ColorModel, cardHeight: Int) {
        this.colorModels.add(colorModel)
        this.cardHeights.add(cardHeight)
        notifyDataSetChanged()
    }

    fun setOnColorClickListener(colorClickListener: ColorClickListener) {
        this.colorClickListener = colorClickListener
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<ColorModel>) {
        this.selectionTracker = selectionTracker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_color, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return colorModels.count()
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(
            colorModels[position],
            cardHeights[position],
            selectionTracker.isSelected(colorModels[position])
        )
    }

    inner class ColorViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), ViewHolderWithDetails<ColorModel>, View.OnClickListener {

        private var selected: Boolean = false

        private val card = itemView.findViewById<CardView>(R.id.card)
        private val title = itemView.findViewById<TextView>(R.id.title)

        fun bind(colorModel: ColorModel, cardHeight: Int, selected: Boolean) {

            this.selected = selected

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
            if(!selected)
                colorClickListener.onClick(colorModels[adapterPosition])
        }

        override fun getItemDetail(): ItemDetailsLookup.ItemDetails<ColorModel> {
            return ColorDetails(adapterPosition, colorModels[adapterPosition])
        }

    }

    interface ColorClickListener {
        fun onClick(colorModel: ColorModel)
    }

}