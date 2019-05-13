package com.zxventures.model

import com.mongodb.client.model.geojson.MultiPolygon
import com.mongodb.client.model.geojson.Point

data class Pdv(
    val id: Int,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val address: Point,
    val coverageArea: MultiPolygon
)