package linc.com.colorsapp.ui.adapters.selection

import androidx.recyclerview.selection.ItemDetailsLookup

interface ViewHolderWithDetails<T> {

    fun getItemDetail(): ItemDetailsLookup.ItemDetails<T>

}