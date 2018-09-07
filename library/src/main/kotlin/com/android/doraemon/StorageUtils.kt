package com.android.doraemon

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences存储
 */
class StorageUtils private constructor(context: Context, repositoryName: String) {

    companion object {
        @JvmStatic
        fun getInstance(context: Context, repositoryName: String? = null): StorageUtils = StorageUtils(context, repositoryName
                ?: context.packageName)
    }

    private val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(repositoryName, Context.MODE_PRIVATE)

    fun put(name: String, any: Any) {
        val editor = sharedPreferences.edit()
        when (any) {
            is Boolean -> {
                editor.putBoolean(name, any)
            }
            is Float -> {
                editor.putFloat(name, any)
            }
            is Int -> {
                editor.putInt(name, any)
            }
            is Long -> {
                editor.putLong(name, any)
            }
            is String -> {
                editor.putString(name, any)
            }
        }
        editor.apply()
    }

    fun put(name: String, any: MutableSet<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(name, any)
        editor.apply()
    }

    fun get(name: String, clazz: Class<*>): Any? = when (clazz) {
        Boolean::class.java -> {
            sharedPreferences.getBoolean(name, false)
        }
        Float::class.java -> {
            sharedPreferences.getFloat(name, 0f)
        }
        Int::class.java -> {
            sharedPreferences.getInt(name, 0)
        }
        Long::class.java -> {
            sharedPreferences.getLong(name, 0L)
        }
        String::class.java -> {
            sharedPreferences.getString(name, null)
        }
        else -> {
            null
        }
    }

    fun get(name: String): MutableSet<String>? = sharedPreferences.getStringSet(name, null)
}