package linc.com.colorsapp.ui.activities

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface NavigatorActivity {

    fun navigateToFragment(fragment: Fragment, withBackStack: Boolean = false, saveInstance: Boolean = false)
    fun navigateToDialog(fragment: DialogFragment)
    fun popBackStack()

}