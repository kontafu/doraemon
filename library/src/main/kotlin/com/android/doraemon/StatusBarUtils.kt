package com.android.doraemon

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.view.View
import android.view.WindowManager
import com.readystatesoftware.systembartint.SystemBarTintManager

/**
 * 状态栏亮色模式，设置状态栏黑色文字、图标，适配4.4以上版本MIUIV6、Flyme和6.0以上版本
 * @return 1:MIUUI 2:Flyme 3:android6.0
 */
fun Activity.setStatusBarLightMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        when {
            onMIUISetStatusBarLightMode() -> {
            }
            onFlymeSetStatusBarLightMode() -> {
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            else -> {
                setStatusBarColor(Color.GRAY)
            }
        }
    }
}

/**
 * 需要MIUIV6以上
 */
private fun Activity.onMIUISetStatusBarLightMode(): Boolean {
    var result = false
    if (window != null) {
        val clazz = window::class.java
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager$".plus("LayoutParams"))
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            result = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } catch (e: Exception) {

        }
    }
    return result
}

/**
 * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
 */
private fun Activity.onFlymeSetStatusBarLightMode(): Boolean {
    var result = false
    try {
        val lp = window.attributes
        val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
        val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
        darkFlag.isAccessible = true
        meizuFlags.isAccessible = true
        val bit = darkFlag.getInt(null)
        var value = meizuFlags.getInt(lp)
        value = bit or value
        meizuFlags.setInt(lp, value)
        window.attributes = lp
        result = true
    } catch (e: Exception) {

    }
    return result
}

/**
 * 修改状态栏颜色，支持4.4以上版本
 */
private fun Activity.setStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.statusBarColor = color
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        transparencyBar(this)
        try {
            SystemBarTintManager(this).apply {
                isStatusBarTintEnabled = true
                setStatusBarTintColor(color)
            }
        } catch (e: Exception) {
        }
    }
}

/**
 * 透明状态栏
 */
private fun transparencyBar(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
}