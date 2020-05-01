package linc.com.colorsapp.utils

class Constants {
    companion object {
        //
        // Storage
        //
        const val DATABASE_NAME = "colors_database"

        //
        // Network
        //
        const val BASE_URL = "https://colours.neilorangepeel.com"

        //
        // Ui
        //

        // Navigation menu
        const val COLORS_FRAGMENT = 0
        const val SAVED_FRAGMENT = 1
        const val OWN_FRAGMENT = 2

        // Selection action menu
        const val FIRST_ITEM = 0

        // RecyclerView
        const val SELECTION_ID = "colors_selection"

        // Transactions/Bundles ids
        const val KEY_COLOR = "colors_key"
        const val KEY_MENU_POSITION = "menu_key"
        const val KEY_CURRENT_FRAGMENT = "current_key"

    }
}