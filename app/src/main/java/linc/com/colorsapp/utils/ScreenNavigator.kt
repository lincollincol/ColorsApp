package linc.com.colorsapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.view.*
import linc.com.colorsapp.R
import linc.com.colorsapp.utils.Constants.Companion.PICKER_FRAGMENT
import javax.inject.Inject

class ScreenNavigator @Inject constructor() {

    class Implementation @Inject constructor(
        private val fragmentManager: FragmentManager,
        @IdRes private val container: Int
    ) : Api {
        private val menuFragments = mutableListOf<Fragment>()

        override fun navigateToFragment(fragment: Fragment, withBackStack: Boolean, saveInstance: Boolean) {
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

        override fun navigateToDialog(fragment: DialogFragment) {
            fragment.show(fragmentManager, fragment::class.java.simpleName)
        }

        override fun popBackStack(): Boolean {
            fragmentManager.popBackStack()
            return when(getCurrentFragment().getName()) {
                PICKER_FRAGMENT -> true
                else -> false
            }
        }

        override fun clearInstances() {
            menuFragments.clear()
        }

        override fun isCurrent(fragmentName: String): Boolean {
            return getCurrentFragment().getName() == fragmentName
        }

        override fun getCurrentFragment(): Fragment  {
            return fragmentManager.findFragmentById(
                R.id.fragmentContainer
            )!!
        }

        private fun startTransaction(fragment: Fragment, withBackStack: Boolean) {
            val transaction = fragmentManager.beginTransaction()
                .replace(container, fragment)
            if(withBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
            transaction.commit()
        }
    }

    interface Api {
        fun navigateToFragment(fragment: Fragment, withBackStack: Boolean = false, saveInstance: Boolean = false)
        fun navigateToDialog(fragment: DialogFragment)
        fun popBackStack(): Boolean
        fun clearInstances()
        fun isCurrent(fragmentName: String): Boolean
        fun getCurrentFragment(): Fragment
    }

}