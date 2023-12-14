/*****************************************************************************
 * AndroidUtil.java
 *
 * Copyright © 2015 VLC authors, VideoLAN and VideoLabs
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 */
package com.zxm.utils.core.device

import android.annotation.SuppressLint
import android.os.Build

object AndroidUtil {
    val isROrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    @SuppressLint("AnnotateVersionCheck")
    val isPOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    @SuppressLint("AnnotateVersionCheck")
    val isOOrLater = isPOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @SuppressLint("AnnotateVersionCheck")
    val isNougatMR1OrLater = isOOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

    @SuppressLint("AnnotateVersionCheck")
    val isNougatOrLater = isNougatMR1OrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @SuppressLint("AnnotateVersionCheck")
    val isMarshMallowOrLater = isNougatOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @SuppressLint("AnnotateVersionCheck")
    val isLolliPopOrLater =
        isMarshMallowOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    @SuppressLint("AnnotateVersionCheck")
    val isKitKatOrLater = isLolliPopOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    val isJellyBeanMR2OrLater =
        isKitKatOrLater || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
}