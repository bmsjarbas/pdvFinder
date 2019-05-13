package com.zxventures.dao

import com.mongodb.client.MongoDatabase
import com.zxventures.model.Pdv

class PdvCollection (val database: MongoDatabase){


    fun loadPdv(id: Int): Pdv {
        TODO()
    }

    fun loadPdv(longitude: Float, latitude: Float): Array<Pdv> {
        TODO()
    }

    fun add(pdv: Pdv) {
       TODO()
    }

}