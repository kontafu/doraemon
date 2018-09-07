package com.android.doraemon

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast

/**
 * 公共toast
 */
fun Activity.toast(message: String) {
    Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
}

/**
 * dp转px
 */
fun dp2px(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

/**
 * px转dp
 */
fun px2dp(px: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().displayMetrics)