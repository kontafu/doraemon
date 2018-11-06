package com.android.doraemon

import android.os.Build
import com.google.android.material.tabs.TabLayout
import android.view.ViewGroup

/**
 * 调整TabLayout指示器宽度
 */
fun reSizeTabLayout(tabLayout: TabLayout, externalMargin: Int, internalMargin: Int) {
    val view = tabLayout.getChildAt(0)
    if (view is ViewGroup) {
        for (index in 0 until view.childCount) {
            val tabView = view.getChildAt(index)
            tabView.minimumWidth = 0
            tabView.setPadding(0, tabView.paddingTop, 0, tabView.paddingBottom)
            if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                when (index) {
                    0 -> {
                        settingMargin(tabView.layoutParams as ViewGroup.MarginLayoutParams, externalMargin, internalMargin)
                    }
                    view.childCount - 1 -> {
                        settingMargin(tabView.layoutParams as ViewGroup.MarginLayoutParams, internalMargin, externalMargin)
                    }
                    else -> {
                        settingMargin(tabView.layoutParams as ViewGroup.MarginLayoutParams, internalMargin, internalMargin)
                    }
                }
            }
        }
        tabLayout.requestLayout()
    }
}

private fun settingMargin(layoutParams: ViewGroup.MarginLayoutParams, start: Int, end: Int) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
        layoutParams.marginStart = start
        layoutParams.marginEnd = end
    } else {
        layoutParams.leftMargin = start
        layoutParams.rightMargin = end
    }
}