package linc.com.colorsapp.ui.adapters.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import linc.com.colorsapp.domain.ColorModel

class ColorLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<ColorModel>() {

    override fun getItemDetails(e: MotionEvent) = recyclerView.findChildViewUnder(e.x, e.y)?.let {
        (recyclerView.getChildViewHolder(it) as? ViewHolderWithDetails<ColorModel>)?.getItemDetail()
    }

}