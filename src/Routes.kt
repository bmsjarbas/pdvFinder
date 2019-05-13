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

        call.respond( pdv ?: "not found")
    }

    post("/pdv") {
        val newPdvRequestData = call.receive<NewPdvRequestData>()
        pdvService.create(newPdvRequestData)
        call.respond(HttpStatusCode.Created, "")
    }

}

data class NewPdvRequestData(
    val id: Int,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val address: GeoJsonModelBase<Array<Double>>,
    val coverageArea : GeoJsonModelBase<Array<Array<Array<Array<Double>>>>>
)

data class GeoJsonModelBase<TCoordinates>(val type: String, val coordinates: TCoordinates)

