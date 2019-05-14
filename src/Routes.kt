package com.zxventures


import com.zxventures.service.PdvService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import org.koin.ktor.ext.inject


fun Route.pdvRoute(){
    val pdvService: PdvService by inject()
    get("/pdv/{id}"){
        val pdvId  = call.parameters["id"]!!.toInt()
        val pdv = pdvService.retrieveBy(pdvId)
        call.respond( pdv ?: HttpStatusCode.NotFound)
    }

    get("/pdv/near/{lon}/{lat}"){
        val longitude  = call.parameters["lon"]!!.toDouble()
        val latitude  = call.parameters["lat"]!!.toDouble()

        val pdv = pdvService.getNearPdvs(longitude, latitude)
        call.respond( pdv ?: HttpStatusCode.NotFound)
    }

    post("/pdv") {
        val newPdvRequestData = call.receive<PdvData>()
        pdvService.create(newPdvRequestData)

        call.respond(HttpStatusCode.Created)
    }

}

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

