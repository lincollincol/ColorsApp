package linc.com.colorsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import linc.com.colorsapp.R
import linc.com.colorsapp.ui.colors.ColorsFragment
import linc.com.colorsapp.ui.onwcolors.OwnColorsFragment
import linc.com.colorsapp.ui.saved.SavedColorsFragment
import linc.com.colorsapp.utils.Constants.Companion.COLORS_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.OWN_FRAGMENT
import linc.com.colorsapp.utils.Constants.Companion.SAVED_FRAGMENT
import linc.com.colorsapp.utils.ScreenNavigator
import nl.joery.animatedbottombar.AnimatedBottomBar


class MainActivity : AppCompatActivity(), NavigatorActivity {

    private lateinit var navigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = ScreenNavigator(supportFragmentManager, R.id.fragmentContainer)

        navigateToMenuFragment(COLORS_FRAGMENT)

        menu.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")
                navigateToMenuFragment(newIndex)
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })

    }

    override fun onBackPressed() {
        if(menu.selectedIndex != COLORS_FRAGMENT) {
            menu.selectTabAt(tabIndex = COLORS_FRAGMENT, animate = true)
            return
        }
        finish()
        super.onBackPressed()
    }

    private fun navigateToMenuFragment(newTab: Int) {
        when(newTab) {
            COLORS_FRAGMENT -> navigator.navigateToFragment(ColorsFragment.newInstance())
            SAVED_FRAGMENT -> navigator.navigateToFragment(SavedColorsFragment.newInstance())
            OWN_FRAGMENT -> navigator.navigateToFragment(OwnColorsFragment.newInstance())
        }
    }

    override fun navigateToFragment(fragment: Fragment) {
        navigator.navigateToFragment(fragment, true)
    }

    override fun navigateToDialog(fragment: DialogFragment) {
        navigator.navigateToDialog(fragment)
    }

    override fun popBackStack() {
        navigator.popBackStack()
    }

}
