package linc.com.colorsapp.ui.adapters

class SelectionManager<T> {

    private val items = mutableListOf<T>()
    private lateinit var itemsLastSelected: MutableList<T>

    private lateinit var selectionAdapter: SelectionAdapter<T>
    private lateinit var selectionListener: SelectionListener

    fun select(item: T) {
        if(items.contains(item)) {
            deselect(item)
            return
        }
        items.add(item)
        selectionListener.selectionChanged(items.count())
        selectionAdapter.selectionChanged(item)
    }

    fun deselect(item: T) {
        items.remove(item)
        if(items.isEmpty()) {
            selectionListener.selectionRemoved()
        }
        selectionListener.selectionChanged(items.count())
        selectionAdapter.selectionChanged(item)
    }

    fun cancelSelection() {
        itemsLastSelected = items.toMutableList()
        items.clear()
        selectionListener.selectionRemoved()
        selectionAdapter.selectionRemoved()
    }

    fun isItemSelected(item: T) = items.contains(item)

    fun isSelectable() = items.isNotEmpty()

    fun attachAdapter(selectionAdapter: SelectionAdapter<T>) {
        this.selectionAdapter = selectionAdapter
    }

    fun setSelectionListener(selectionListener: SelectionListener) {
        this.selectionListener = selectionListener
    }

    fun getLastSelected() = items

    interface SelectionAdapter<T> {
        fun selectionChanged(item: T)
        fun selectionRemoved()
    }

    interface SelectionListener {
        fun selectionChanged(count: Int)
        fun selectionRemoved()
    }
}