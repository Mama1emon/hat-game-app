package dev.mama1emon.hat.addteam.domain.models

import androidx.annotation.StringRes

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
class Player private constructor(
    val name: String,
    val hasError: Boolean,
    @StringRes val errorResId: Int?
) {

    object Factory {

        fun create(name: String = "") = Player(name = name, hasError = false, errorResId = null)

        fun createWithError(name: String = "", @StringRes errorResId: Int): Player {
            return Player(name = name, hasError = true, errorResId = errorResId)
        }
    }
}
