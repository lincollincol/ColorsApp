package linc.com.colorsapp.ui.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.updateAll
import javax.inject.Inject


class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>(),
    SelectionManager.SelectionAdapter<ColorModel> {

    private val colorModels = mutableListOf<ColorModel>()
    private val cardHeights = mutableListOf<Int>()
    private lateinit var colorClickListener: ColorClickListener
    private lateinit var selectionManager: SelectionManager<ColorModel>

    fun setColors(colorModels: MutableList<ColorModel>, cardHeights: List<Int>) {

        this.colorModels.updateAll(colorModels)
        this.cardHeights.updateAll(cardHeights)
        //todo ???
        notifyItemRangeChanged(0, colorModels.count())
        notifyDataSetChanged()
    }

    fun deleteColor(colorModel: ColorModel) {
        val position = this.colorModels.indexOf(colorModel)
        this.colorModels.removeAt(position)
        this.cardHeights.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertColor(colorModel: ColorModel, cardHeight: Int) {
        this.colorModels.add(colorModel)
        this.cardHeights.add(cardHeight)
        notifyItemInserted(colorModels.count())
    }

    override fun selectionChanged(item: ColorModel) {
        notifyItemChanged(colorModels.indexOf(item))
    }

    override fun selectionRemoved(rangeStart: Int, rangeEnd: Int) {
        // todo const 1 to last element
        notifyItemRangeChanged(rangeStart, rangeEnd-rangeStart+1)
    }

    fun setOnColorClickListener(colorClickListener: ColorClickListener) {
        this.colorClickListener = colorClickListener
    }

    fun setSelectionManager(selectionManager: SelectionManager<ColorModel>) {
        this.selectionManager = selectionManager
        this.selectionManager.attachAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(
            colorModels[position],
            cardHeights[position],
            selectionManager.isItemSelected(colorModels[position])
        )
    }

    override fun getItemCount(): Int {
        return colorModels.count()
    }

    inner class ColorViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener
    {

        private val card = itemView.findViewById<FrameLayout>(R.id.card)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val iconSelected = itemView.findViewById<ImageView>(R.id.iconSelected)
        private val iconSaved = itemView.findViewById<ImageView>(R.id.iconSaved)

        fun bind(colorModel: ColorModel, cardHeight: Int, selected: Boolean) {

            @ColorInt val readableColor = ColorUtil
                .getReadableColor(Color.parseColor(colorModel.hex))

            title.apply {
                text = colorModel.title
                setTextColor(readableColor)
            }

            card.apply {
                background.setColorFilter(
                    Color.parseColor(colorModel.hex),
                    PorterDuff.Mode.SRC_IN
                )
                isSelected = selected
                minimumHeight = cardHeight
            }

            iconSelected.apply {
                iconSelected.visibility = if(selected) View.VISIBLE else View.GONE
                setBackgroundResource(when(readableColor) {
                    Color.WHITE-> R.drawable.ic_selected_white
                    else -> R.drawable.ic_selected_black
                })
            }

            iconSaved.apply {
                visibility = if(colorModel.saved) View.VISIBLE else View.GONE
                setBackgroundResource(when(readableColor) {
                    Color.WHITE-> R.drawable.ic_save_white
                    else -> R.drawable.ic_save_black
                })
            }

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            if(!selectionManager.isSelectable())
                colorClickListener.onClick(colorModels[adapterPosition])
            else {
                selectionManager.select(colorModels[adapterPosition], adapterPosition)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            selectionManager.select(colorModels[adapterPosition], adapterPosition)
            return true
        }

    }

    interface ColorClickListener {
        fun onClick(colorModel: ColorModel)
    }

}













/*




package linc.com.colorsapp.ui.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionManager
import linc.com.colorsapp.R
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.ui.adapters.selection.ColorDetails
import linc.com.colorsapp.ui.adapters.selection.ViewHolderWithDetails
import linc.com.colorsapp.utils.ColorUtil
import linc.com.colorsapp.utils.updateAll
import kotlin.random.Random


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

    fun deleteColor(colorModel: ColorModel) {
        val position = this.colorModels.indexOf(colorModel)
        this.colorModels.removeAt(position)
        this.cardHeights.removeAt(position)
        notifyItemRemoved(position)
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
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_color, parent, false)
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
        private val card = itemView.findViewById<FrameLayout>(R.id.card)
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val iconSelected = itemView.findViewById<ImageView>(R.id.iconSelected)

        fun bind(colorModel: ColorModel, cardHeight: Int, selected: Boolean) {
            this.selected = selected

            TransitionManager.beginDelayedTransition(card,
                Fade(Fade.IN)
                    .setDuration(500)
                    .setInterpolator(AccelerateDecelerateInterpolator()))

            @ColorInt val readableColor = ColorUtil
                .getReadableColor(Color.parseColor(colorModel.hex))

            title.apply {
                text = colorModel.name
                setTextColor(readableColor)
            }

            card.apply {
                background.setColorFilter(
                        Color.parseColor(colorModel.hex),
                        PorterDuff.Mode.SRC_IN
                )
                isSelected = selected
                minimumHeight = cardHeight
            }

            iconSelected.apply {
                iconSelected.visibility = if(selected) View.VISIBLE else View.GONE
                setBackgroundResource(when(readableColor) {
                    Color.WHITE-> R.drawable.ic_selected_white
                    else -> R.drawable.ic_selected_black
                })
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



 */