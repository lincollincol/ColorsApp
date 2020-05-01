package linc.com.colorsapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ScreenNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {

    private val menuFragments = mutableListOf<Fragment>()
    private lateinit var currentFragmentName: String

    fun navigateToFragment(fragment: Fragment, withBackStack: Boolean = false) {
        if(menuFragments.any { it.getName() == fragment.getName() }) {
            startTransaction(
                menuFragments[menuFragments.indexOfFirst {
                    it.getName() == fragment.getName()
                }],
                withBackStack
            )
        } else {
            menuFragments.add(fragment)
            startTransaction(fragment, withBackStack)
        }
        currentFragmentName = fragment.getName() ?: "DEF"
    }

    fun navigateToDialog(fragment: DialogFragment) {
        fragment.show(fragmentManager, fragment::class.java.simpleName)
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun getCurrentFragment() = menuFragments[menuFragments.indexOfFirst {
        it.getName() == currentFragmentName
    }]

    fun clearInstances() {
        menuFragments.clear()
    }

    private fun startTransaction(fragment: Fragment, withBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
            .replace(container, fragment)
        if(withBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }


}