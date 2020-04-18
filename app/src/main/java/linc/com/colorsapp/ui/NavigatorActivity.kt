package linc.com.colorsapp.ui

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface NavigatorActivity {

    fun navigateToFragment(fragment: Fragment)
    fun navigateToDialog(fragment: DialogFragment)
    fun popBackStack()

}