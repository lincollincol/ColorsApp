package linc.com.colorsapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.view.*
import linc.com.colorsapp.R
import linc.com.colorsapp.utils.Constants.Companion.PICKER_FRAGMENT

class ScreenNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {

    private val menuFragments = mutableListOf<Fragment>()

    fun navigateToFragment(fragment: Fragment, withBackStack: Boolean = false, saveInstance: Boolean = false) {
        if(menuFragments.any { it.getName() == fragment.getName() }) {
            startTransaction(
                menuFragments[menuFragments.indexOfFirst {
                    it.getName() == fragment.getName()
                }],
                withBackStack
            )
        } else {
            if(saveInstance) {
                menuFragments.add(fragment)
            }
            startTransaction(fragment, withBackStack)
        }
    }

    fun navigateToDialog(fragment: DialogFragment) {
        fragment.show(fragmentManager, fragment::class.java.simpleName)
    }

    fun popBackStack(): Boolean {
        fragmentManager.popBackStack()
        return when(getCurrentFragment()?.getName()) {
            PICKER_FRAGMENT -> true
            else -> false
        }
    }

    fun getCurrentFragment() = fragmentManager
        .findFragmentById(R.id.fragmentContainer)

    fun clearInstances() {
        menuFragments.clear()
    }

    fun isCurrent(fragmentName: String): Boolean {
        return getCurrentFragment()?.getName() == fragmentName
    }

    private fun startTransaction(fragment: Fragment, withBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
            .replace(container, fragment)
        if(withBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }


}