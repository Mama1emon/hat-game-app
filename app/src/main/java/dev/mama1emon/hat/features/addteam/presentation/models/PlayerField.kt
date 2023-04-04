package dev.mama1emon.hat.features.addteam.presentation.models

import androidx.annotation.StringRes
import java.util.*

/**
 * @author Andrew Khokhlov on 19/03/2023
 */
class PlayerField private constructor(
    val id: UUID,
    val name: String,
    val hasError: Boolean,
    @StringRes val errorResId: Int?
) {

    object Factory {

        fun create(id: UUID = UUID.randomUUID(), name: String = ""): PlayerField {
            return PlayerField(id = id, name = name, hasError = false, errorResId = null)
        }

        fun createWithError(id: UUID, name: String = "", @StringRes errorResId: Int): PlayerField {
            return PlayerField(id = id, name = name, hasError = true, errorResId = errorResId)
        }
    }
}
