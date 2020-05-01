package linc.com.colorsapp.ui.adapters

class SelectionManager<T> {

    private val items = mutableListOf<T>()
    private val itemsPositions = mutableListOf<Int>()

    private lateinit var selectionAdapter: SelectionAdapter<T>
    private lateinit var selectionListener: SelectionListener

    fun select(item: T, position: Int) {
        if(items.contains(item)) {
            deselect(item, position)
            return
        }
        items.add(item)
        itemsPositions.add(position)
        selectionListener.selectionChanged(items.count())
        selectionAdapter.selectionChanged(item)
    }

    fun deselect(item: T, position: Int) {
        items.remove(item)
        itemsPositions.add(position)
        if(items.isEmpty()) {
            selectionListener.selectionRemoved()
        }
        selectionListener.selectionChanged(items.count())
        selectionAdapter.selectionChanged(item)
    }

    fun cancelSelection() {
        selectionAdapter.selectionRemoved(itemsPositions.min() ?: 0, itemsPositions.max() ?: 0)
        selectionListener.selectionRemoved()
        items.clear()
        itemsPositions.clear()
    }

    fun isItemSelected(item: T) = items.contains(item)

    fun isSelectable() = items.isNotEmpty()

    fun attachAdapter(selectionAdapter: SelectionAdapter<T>) {
        this.selectionAdapter = selectionAdapter
    }

    fun setSelectionListener(selectionListener: SelectionListener) {
        this.selectionListener = selectionListener
    }

    fun getSelected() = items

    interface SelectionAdapter<T> {
        fun selectionChanged(item: T)
        fun selectionRemoved(rangeStart: Int, rangeEnd: Int)
    }

    interface SelectionListener {
        fun selectionChanged(count: Int)
        fun selectionRemoved()
    }
}