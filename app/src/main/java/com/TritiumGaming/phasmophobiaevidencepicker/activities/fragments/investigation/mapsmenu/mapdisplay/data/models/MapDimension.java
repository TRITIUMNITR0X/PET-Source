package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.util.Log;

public class MapDimension {

    public int w, h;

    public MapDimension(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public synchronized void print() {
        Log.d("Maps", "[ W: " + w + " H: " + h + " ]");
    }
}
