package com.zxventures.service

import com.zxventures.dao.PdvCollection
import com.zxventures.dto.PdvData

interface PdvService {
    fun create(pdvData: PdvData)
    fun retrieveBy(id: Int) : PdvData?
    fun getNearPdvs(longitude: Double, latitude: Double) : List<PdvData>?
}