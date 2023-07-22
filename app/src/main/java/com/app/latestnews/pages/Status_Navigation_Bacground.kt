package com.app.latestnews.pages

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat


fun setFullScreen(window: Window?) {
        WindowCompat.setDecorFitsSystemWindows(window!!, false)
}

fun lightStatusBar(window: Window, isLight: Boolean?, isLightNav: Boolean?) {
        val wic = WindowInsetsControllerCompat(window, window.decorView)
        wic.isAppearanceLightStatusBars = isLight!!
        wic.isAppearanceLightNavigationBars = isLightNav!!
}

