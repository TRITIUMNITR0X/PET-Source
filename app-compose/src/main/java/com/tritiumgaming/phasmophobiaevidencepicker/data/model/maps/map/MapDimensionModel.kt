package com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map

import android.util.Log

class MapDimensionModel(val w: Int, val h: Int) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}
