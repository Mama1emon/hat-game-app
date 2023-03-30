package dev.mama1emon.hat.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * @author Andrew Khokhlov on 23/03/2023
 */
@Parcelize
data class Player(
    val id: UUID,
    val name: String,
    val wordlist: List<String> = emptyList()
) : Parcelable