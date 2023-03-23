package dev.mama1emon.hat.domain.models

import java.util.*

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
data class Team(val id: UUID, val name: String, val players: List<Player>)