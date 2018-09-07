package com.android.doraemon

import android.view.View

private var lastClickTime = 0L

private var interval = 600L

/**
 * 设置点击间隔，ms
 */
fun <T : View> T.with(delay: Long = 600L): T {
    interval = delay
    return this
}

/**
 * 防点击抖动事件
 */
fun <T : View> T.onClick(block: (T) -> Unit) {
    setOnClickListener {
        val currentClickTime = System.currentTimeMillis()
        if (interval < currentClickTime - lastClickTime) {
            lastClickTime = currentClickTime
            block.invoke(this)
        }
    }
}