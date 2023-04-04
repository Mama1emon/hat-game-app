package dev.mama1emon.hat.features.addteam.presentation.models

import androidx.annotation.StringRes
import java.util.*

/**
 * @author Andrew Khokhlov on 18/03/2023
 */
class TeamField private constructor(
    val id: UUID,
    val name: String,
    val hasError: Boolean,
    @StringRes val errorResId: Int?
) {
    object Factory {
        fun create(id: UUID = UUID.randomUUID(), name: String = ""): TeamField {
            return TeamField(id = id, name = name, hasError = false, errorResId = null)
        }

        fun createWithError(id: UUID, name: String, @StringRes errorResId: Int): TeamField {
            return TeamField(id = id, name = name, hasError = true, errorResId = errorResId)
        }
    }
}
