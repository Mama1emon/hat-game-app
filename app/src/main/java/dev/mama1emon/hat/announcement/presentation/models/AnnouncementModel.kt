package dev.mama1emon.hat.announcement.presentation.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * @author Andrew Khokhlov on 21/03/2023
 */
@Parcelize
data class AnnouncementModel(
    @StringRes val titleResId: Int,
    @DrawableRes val imageResId: Int,
    val description: String,
    val buttonText: String,
    val onButtonClick: () -> Unit
) : Parcelable
