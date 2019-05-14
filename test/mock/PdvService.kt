package com.zxventures.mock

import com.zxventures.dto.Address
import com.zxventures.dto.CoverageArea
import com.zxventures.dto.PdvData
import com.zxventures.service.PdvService


class PdvServiceFake : PdvService {


    override fun create(pdvData: PdvData) {
        //do nothing since the parser already validates the json in when handling the post.

    }

    override fun retrieveBy(id: Int): PdvData? {
        if (id == 0)
            return null
        return PdvData(
            id,
            "tradingName",
            "asd",
            "asd",
            Address(),
            CoverageArea()
        )

    }

    override fun getNearPdvs(longitude: Double, latitude: Double): List<PdvData>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}