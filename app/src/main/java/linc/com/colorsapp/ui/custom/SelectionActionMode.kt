package linc.com.colorsapp.ui.custom

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.annotation.RawRes
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionTracker
import linc.com.colorsapp.R
import linc.com.colorsapp.utils.Constants.Companion.FIRST_ITEM

class SelectionActionMode<T>(
    private val context: Context,
    private val selectionTracker: SelectionTracker<T>,
    private val type: Type
) : ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        println("Action ${type.name}")
        mode?.finish()
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_selection_action_mode, menu)

        @RawRes val menuIcon = when(type) {
            Type.SAVE -> R.drawable.ic_save
            Type.DELETE -> R.drawable.ic_delete
        }

        menu?.getItem(FIRST_ITEM)?.icon = ContextCompat.getDrawable(context, menuIcon)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        selectionTracker.clearSelection()
    }

    enum class Type{
        SAVE, DELETE
    }

}