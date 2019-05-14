package com.zxventures.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.client.model.geojson.MultiPolygon
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.PolygonCoordinates
import com.mongodb.client.model.geojson.Position
import com.zxventures.dao.PdvCollection
import com.zxventures.dto.Address
import com.zxventures.dto.CoverageArea
import com.zxventures.dto.GeoJsonModelBase
import com.zxventures.dto.PdvData
import com.zxventures.model.Pdv

class PdvManager(private val pdvCollection: PdvCollection) : PdvService{


    override fun create(pdvData: PdvData){
        val address = parseAddress(pdvData.address)
        val coverageArea = parseCoverageArea(pdvData.coverageArea)
        val pdv = Pdv(pdvData.id, pdvData.tradingName, pdvData.ownerName, pdvData.document, address, coverageArea)
        pdvCollection.add(pdv)
    }

    override fun retrieveBy(id: Int) : PdvData? {
        val pdv = pdvCollection.loadPdv(id)
        pdv?.let {
            return parseToPdvData(pdv)
        }

        return null
    }

    override fun getNearPdvs(longitude: Double, latitude: Double) : List<PdvData>?{
        val result = pdvCollection.loadPdv(longitude, latitude)
        return result.map { item -> parseToPdvData(item) }.toList()
    }

    private fun parseToPdvData(pdv: Pdv): PdvData {
        val address = ObjectMapper().readValue(pdv.address.toJson(), Address::class.java)
        val coverageArea = ObjectMapper().readValue(pdv.coverageArea.toJson(), CoverageArea::class.java)
        val pdvDetails = PdvData(pdv.id, pdv.tradingName, pdv.ownerName, pdv.document, address, coverageArea)
        return pdvDetails
    }

    private fun parseAddress(geoJsonModel: GeoJsonModelBase<Array<Double>>) : Point{
        val point = Point(Position(geoJsonModel.coordinates.toMutableList()))
        return point
    }

    private fun parseCoverageArea( geoJsonModel: GeoJsonModelBase<Array<Array<Array<Array<Double>>>>>) : MultiPolygon{
        var listOfPoligonCoordinates = mutableListOf<PolygonCoordinates>()
        val multiPolygon = MultiPolygon(listOfPoligonCoordinates)
        for( x in geoJsonModel.coordinates){
            for (y in x){
                var listOfPositions = mutableListOf<Position>()

                for(z in y){
                    val position = Position(z.toMutableList())
                    listOfPositions.add(position)
                }

                val polygonCoordinates = PolygonCoordinates(listOfPositions)
                listOfPoligonCoordinates.add(polygonCoordinates)
            }
        }

        return multiPolygon
    }



}