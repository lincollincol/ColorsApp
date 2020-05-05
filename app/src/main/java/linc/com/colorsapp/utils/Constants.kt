package linc.com.colorsapp.utils

class Constants {
    companion object {
        // Storage
        const val DATABASE_NAME = "colors_database"
        const val COLORS_TABLE_NAME = "colors"

        // Network
        const val BASE_URL = "https://colours.neilorangepeel.com"

        // Parsing
        const val PARSE_COLOR_TITLE = 0
        const val PARSE_COLOR_HEX = 1
        const val PARSE_COLOR_RGB = 2


        // Navigation
        const val COLORS_FRAGMENT = 0
        const val SAVED_FRAGMENT = 1
        const val OWN_FRAGMENT = 2

        const val MAIN_FRAGMENT = "ColorsFragment"
        const val PICKER_FRAGMENT = "NewColorFragment"

        // Selection action menu
        const val ONE_ITEM_SELECTED = 1
        const val EMPTY_SELECTION = 0

        // Transactions/Bundles ids
        const val KEY_COLOR_MODEL = "color_model_key"
        const val KEY_MENU_POSITION = "menu_key"
        const val KEY_CURRENT_FRAGMENT = "current_key"
        const val KEY_COLOR = "color_key"

        // String formats
        const val FORMAT_HEX = "#%06X"
        const val FORMAT_RGB = "rgb(%d, %d, %d)"
    }
}