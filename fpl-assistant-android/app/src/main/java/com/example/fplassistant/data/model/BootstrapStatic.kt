package com.example.fplassistant.data.model

import com.squareup.moshi.Json

data class BootstrapStatic(
    val elements: List<Element> = emptyList(),
    val teams: List<TeamRef> = emptyList(),
    @Json(name = "element_types") val elementTypes: List<ElementTypeRef> = emptyList()
)

data class Element(
    val id: Int,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "second_name") val secondName: String,
    @Json(name = "web_name") val webName: String,
    @Json(name = "now_cost") val nowCost: Int,
    @Json(name = "element_type") val elementTypeId: Int,
    val team: Int,
    @Json(name = "selected_by_percent") val selectedByPercent: String,
    val form: String,
    @Json(name = "points_per_game") val pointsPerGame: String,
    @Json(name = "total_points") val totalPoints: Int,
    @Json(name = "ict_index") val ictIndex: String,
    val status: String
)

data class TeamRef(
    val id: Int,
    val name: String,
    @Json(name = "short_name") val shortName: String
)

data class ElementTypeRef(
    val id: Int,
    @Json(name = "singular_name_short") val shortName: String
)