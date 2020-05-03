package linc.com.colorsapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*
import linc.com.colorsapp.R
import linc.com.colorsapp.ui.colors.ColorsFragment
import linc.com.colorsapp.ui.onwcolors.OwnColorsFragment
import linc.com.colorsapp.ui.saved.SavedColorsFragment
import linc.com.colorsapp.utils.Constants.Companion.COLORS_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.KEY_CURRENT_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.KEY_MENU_POSITION
import linc.com.colorsapp.utils.Constants.Companion.MAIN_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.OWN_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.SAVED_FRAGMENT
import linc.com.colorsapp.utils.ScreenNavigator
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity :
    AppCompatActivity(),
    AnimatedBottomBar.OnTabSelectListener,
    NavigatorActivity,
    BottomMenuActivity,
    ToolbarActivity,
    InputActivity
{

    private lateinit var navigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = ScreenNavigator(supportFragmentManager, R.id.fragmentContainer)
        menu.setOnTabSelectListener(this)

        if(savedInstanceState != null) {
            menu.selectTabAt(savedInstanceState.getInt(KEY_MENU_POSITION))
            navigator.navigateToFragment(
                supportFragmentManager.getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT)!!
            )
            return
        }

        navigator.navigateToFragment(ColorsFragment.newInstance())

    }

    override fun onTabSelected(
        lastIndex: Int,
        lastTab: AnimatedBottomBar.Tab?,
        newIndex: Int,
        newTab: AnimatedBottomBar.Tab
    ) {
        when(newIndex) {
            COLORS_FRAGMENT -> navigator.navigateToFragment(ColorsFragment.newInstance(), saveInstance = true)
            SAVED_FRAGMENT -> navigator.navigateToFragment(SavedColorsFragment.newInstance(), saveInstance = true)
            OWN_FRAGMENT -> navigator.navigateToFragment(OwnColorsFragment.newInstance(), saveInstance = true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MENU_POSITION, menu.selectedIndex)
        supportFragmentManager.putFragment(outState, KEY_CURRENT_FRAGMENT, navigator.getCurrentFragment()!!)
        navigator.clearInstances()
    }

    override fun onBackPressed() {
        if(navigator.isCurrent(MAIN_FRAGMENT)) {
            navigator.clearInstances()
            finish()
            super.onBackPressed()
        }else {
            if(!navigator.popBackStack())
                menu.selectTabAt(tabIndex = COLORS_FRAGMENT, animate = true)
        }

    }


    override fun navigateToFragment(fragment: Fragment, withBackStack: Boolean, saveInstance: Boolean) {
        navigator.navigateToFragment(fragment, withBackStack, saveInstance)
    }

    override fun navigateToDialog(fragment: DialogFragment) {
        navigator.navigateToDialog(fragment)
    }

    override fun popBackStack() {
        navigator.popBackStack()
    }

    override fun showMenu() {
        TransitionManager.beginDelayedTransition(
            main_layout,
            Slide(Gravity.BOTTOM)
        )
        menu.visibility = View.VISIBLE
    }

    override fun hideMenu() {
        TransitionManager.beginDelayedTransition(
            main_layout,
            Slide(Gravity.BOTTOM)
        )
        menu.visibility = View.GONE
    }

    override fun hideToolbar() {
        supportActionBar?.hide()
    }

    override fun showToolbar() {
        supportActionBar?.show()
    }

    override fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                if(isAcceptingText) hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

}
