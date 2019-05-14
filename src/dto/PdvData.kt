package com.zxventures.dto

data class PdvData(
    val id: Int,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val address: Address,
    val coverageArea : CoverageArea
)

class Address(type: String, coordinates: Array<Double>) : GeoJsonModelBase<Array<Double>>(type, coordinates)
{
    constructor() : this("", emptyArray())
}
class CoverageArea(type: String, coordinates:Array<Array<Array<Array<Double>>>>)
    : GeoJsonModelBase<Array<Array<Array<Array<Double>>>>>(type, coordinates)
{
    constructor() : this("", emptyArray())
}

open class GeoJsonModelBase<TCoordinates>(val type: String, val coordinates: TCoordinates)
{
}