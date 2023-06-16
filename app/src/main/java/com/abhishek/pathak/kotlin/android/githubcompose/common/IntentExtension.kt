package com.abhishek.pathak.kotlin.android.githubcompose.common

import android.content.Intent
import android.net.Uri

fun buildUrlIntent(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url))