package com.zxventures.dao

import com.mongodb.QueryBuilder
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.zxventures.model.Pdv
import org.litote.kmongo.*

class PdvCollection (private val database: MongoDatabase){
    private val mongoCollection = database.getCollection<Pdv>()

    fun loadPdv(id: Int): Pdv? {
        val result = mongoCollection.findOneById(id)
        return  result
    }

    fun loadPdv(longitude: Double, latitude: Double): Iterable<Pdv> {
       val result = mongoCollection.find(Filters.nearSphere("coverageArea",longitude, latitude, 10000.0, 1.0))
        return result
    }

    fun add(pdv: Pdv) {
       database.getCollection<Pdv>().save(pdv)
    }

}