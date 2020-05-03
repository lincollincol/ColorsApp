package linc.com.colorsapp.ui.custom

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RawRes
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import linc.com.colorsapp.R
import linc.com.colorsapp.ui.adapters.SelectionManager

class SelectionActionMode<T>(
    private val context: Context,
    private val selectionManager: SelectionManager<T>,
    private val onActionClickListener: OnActionClickListener,
    private val type: Type
) : ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        onActionClickListener.onActionClick(item)
        mode?.finish()
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_selection_action_mode, menu)

        @RawRes val menuIcon = when(type) {
            Type.SAVE -> R.drawable.ic_save_white
            Type.DELETE -> R.drawable.ic_delete
        }

        menu?.findItem(R.id.action)?.icon =
            ContextCompat.getDrawable(context, menuIcon)

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        selectionManager.cancelSelection()
    }

    interface OnActionClickListener {
        fun onActionClick(item: MenuItem?)
    }

    enum class Type{
        SAVE, DELETE
    }

}