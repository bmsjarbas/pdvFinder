package com.zxventures.service

import com.zxventures.NewPdvRequestData
import com.zxventures.dao.PdvCollection
import com.zxventures.model.Pdv

class PdvService(val pdvCollection: PdvCollection){


    fun create(pdvData: NewPdvRequestData){
        TODO()
    }


    fun retrieveBy(id: Int) : Pdv {
        TODO()
    }
}