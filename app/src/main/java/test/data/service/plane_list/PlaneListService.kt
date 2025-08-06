package test.data.service.plane_list

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import test.core.resolveStatusCodeWith
import test.data.service.local_cache.PlaneFileReader
import test.data.service.plane_list.response.AeroplaneResponse
import test.data.service.plane_list.response.toPortfolio
import test.domain.PlaneRepository
import test.domain.model.Portfolio
import javax.inject.Inject

class PlaneListService @Inject constructor(
    retrofit: Retrofit,
    private val dataObject: PlaneFileReader
) : PlaneRepository {

    private val client = retrofit.create(Client::class.java)

    override suspend fun getPortfolio(): Portfolio =
//        resolveStatusCodeWith(client.getPlaneList()).toPortfolio()
//        resolveStatusCodeWith(dataObject.getPlaneList()).toPortfolio()
        resolveStatusCodeWith(dataObject.getSlowPlaneList()).toPortfolio()
//        resolveStatusCodeWith(dataObject.getMalformedPlaneList()).toPortfolio()
//        resolveStatusCodeWith(dataObject.getEmptyPlaneList()).toPortfolio()
    //endregion

    interface Client {
        @GET("plane-response.json")
        suspend fun getPlaneList(): Response<AeroplaneResponse>
    }

    interface DataObject {
        suspend fun getPlaneList(): Response<AeroplaneResponse>
        suspend fun getSlowPlaneList(): Response<AeroplaneResponse>
        suspend fun getMalformedPlaneList(): Response<AeroplaneResponse>
        suspend fun getEmptyPlaneList(): Response<AeroplaneResponse>
    }
}