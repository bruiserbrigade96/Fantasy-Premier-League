package com.example.fplassistant.data.repository

import com.example.fplassistant.data.model.BootstrapStatic
import com.example.fplassistant.data.model.Element
import com.example.fplassistant.data.model.ElementTypeRef
import com.example.fplassistant.data.model.TeamRef
import com.example.fplassistant.data.remote.FplApi

class FplRepository(
    private val api: FplApi
) {
    @Volatile private var cachedBootstrap: BootstrapStatic? = null

    suspend fun getBootstrap(forceRefresh: Boolean = false): BootstrapStatic {
        val current = cachedBootstrap
        if (current != null && !forceRefresh) return current
        return api.getBootstrapStatic().also { cachedBootstrap = it }
    }

    suspend fun getPlayersUi(forceRefresh: Boolean = false): List<UiPlayer> {
        val data = getBootstrap(forceRefresh)
        val teamById: Map<Int, TeamRef> = data.teams.associateBy { it.id }
        val typeById: Map<Int, ElementTypeRef> = data.elementTypes.associateBy { it.id }
        return data.elements.map { element ->
            mapElementToUi(element, teamById, typeById)
        }
    }

    private fun mapElementToUi(
        element: Element,
        teamById: Map<Int, TeamRef>,
        typeById: Map<Int, ElementTypeRef>
    ): UiPlayer {
        val name = element.webName.ifBlank { "${element.firstName} ${element.secondName}" }
        val teamShort = teamById[element.team]?.shortName ?: ""
        val position = typeById[element.elementTypeId]?.shortName ?: ""
        val price = element.nowCost / 10.0
        val form = element.form.toDoubleOrNull() ?: 0.0
        val selectedBy = element.selectedByPercent.toDoubleOrNull() ?: 0.0
        val ppg = element.pointsPerGame.toDoubleOrNull() ?: 0.0
        val ict = element.ictIndex.toDoubleOrNull() ?: 0.0
        return UiPlayer(
            id = element.id,
            name = name,
            team = teamShort,
            position = position,
            price = price,
            totalPoints = element.totalPoints,
            form = form,
            pointsPerGame = ppg,
            selectedByPercent = selectedBy,
            ictIndex = ict,
            status = element.status
        )
    }
}

data class UiPlayer(
    val id: Int,
    val name: String,
    val team: String,
    val position: String,
    val price: Double,
    val totalPoints: Int,
    val form: Double,
    val pointsPerGame: Double,
    val selectedByPercent: Double,
    val ictIndex: Double,
    val status: String
)