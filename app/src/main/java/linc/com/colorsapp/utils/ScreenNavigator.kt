package linc.com.colorsapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ScreenNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {

    fun navigateToFragment(fragment: Fragment, withBackStack: Boolean = false) {
        val transaction = fragmentManager.beginTransaction()
            .replace(container, fragment)
        println(fragment::class.java.simpleName)
        if(withBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }

    fun navigateToDialog(fragment: DialogFragment) {
        fragment.show(fragmentManager, fragment::class.java.simpleName)
        println(fragment::class.java.simpleName)
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

}