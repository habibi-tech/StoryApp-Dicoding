package com.habibi.core.utils

import android.content.Context
import android.location.Geocoder
import java.io.IOException
import java.util.Locale

fun Context.getAddressName(lat: Double, lon: Double): String? {
    var addressName: String? = null
    val geocoder = Geocoder(this, Locale.getDefault())
    try {
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list != null && list.size != 0) {
            addressName = list[0].getAddressLine(0)
        }
    } catch (_: IOException) {}
    return addressName
}