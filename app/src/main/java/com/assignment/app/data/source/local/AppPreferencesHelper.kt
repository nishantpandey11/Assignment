package com.assignment.app.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.assignment.app.utils.CURRENT_OFFSET
import com.assignment.app.utils.OFFSET_PREF


class AppPreferencesHelper constructor(context: Context) {

    lateinit var mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(OFFSET_PREF, Context.MODE_PRIVATE)
    }

    fun setOffset(offset: Int) {
        mPrefs.edit().putInt(CURRENT_OFFSET, offset).apply();
    }

    fun getOffset(): Int {
        return mPrefs.getInt(CURRENT_OFFSET, 0)
    }
}