package dev.mama1emon.hat.addteam.domain.models

import androidx.annotation.StringRes
import java.util.*

/**
 * @author Andrew Khokhlov on 18/03/2023
 */
class Team private constructor(
    val id: UUID,
    val name: String,
    val hasError: Boolean,
    @StringRes val errorResId: Int?
) {
    object Factory {
        fun create(id: UUID = UUID.randomUUID(), name: String = ""): Team {
            return Team(id = id, name = name, hasError = false, errorResId = null)
        }

        fun createWithError(
            id: UUID = UUID.randomUUID(),
            name: String,
            @StringRes errorResId: Int
        ): Team {
            return Team(id = id, name = name, hasError = true, errorResId = errorResId)
        }
    }
}
