package br.com.wifigps.permissions

import android.content.Intent
import android.os.Build
import android.provider.Settings
import br.com.wifigps.MainActivity

fun buildMinVersionR(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

fun buildMinVersionQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

fun buildMinVersionP(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun buildVersionP(): Boolean = Build.VERSION.SDK_INT == Build.VERSION_CODES.P

fun buildMinVersionN(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun buildMinVersionM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun buildMinVersionL(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

internal fun makeIntent(action: String): Intent = Intent(action)

internal fun MainActivity.startLocationSettings() =
    this.startActivity(makeIntent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

internal fun MainActivity.startWiFiSettings() =
    this.startActivityForResult(makeIntent(Settings.Panel.ACTION_WIFI), 0)
