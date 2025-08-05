package test.data.service.local_cache

import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import test.AerApplication
import test.R
import test.data.service.plane_list.PlaneListService
import test.data.service.plane_list.response.AeroplaneResponse
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * PlaneFileReader
 *
 * This class specifically interacts with the R.raw. exercise files provided and places
 * the JSON object found into the PlaneFileReader class.
 *
 * This is setup like this to mimimise the differences between handling this JSON file directly,
 * VS setting up an API to call.
 *
 * @throws Exception The JSON format cannot be mapped to the Response class
 */
class PlaneFileReader @Inject constructor(
    private val moshi: Moshi
) : PlaneListService.DataObject {

    override suspend fun getPlaneList(): Response<AeroplaneResponse> =
            getPlaneJSON(AerApplication.getAppContext().resources.openRawResource(R.raw.successful_aeroplane_response))

    override suspend fun getSlowPlaneList(): Response<AeroplaneResponse> {
        delay(5000)
        return getPlaneJSON(AerApplication.getAppContext().resources.openRawResource(R.raw.successful_aeroplane_response))
    }

    override suspend fun getMalformedPlaneList(): Response<AeroplaneResponse> =
            getPlaneJSON(AerApplication.getAppContext().resources.openRawResource(R.raw.malformed_response), errorReturn = true)

    override suspend fun getEmptyPlaneList(): Response<AeroplaneResponse> =
            getPlaneJSON(AerApplication.getAppContext().resources.openRawResource(R.raw.empty_response))

    private fun getPlaneJSON(file:InputStream, errorReturn:Boolean = false): Response<AeroplaneResponse> {
        var planeList: AeroplaneResponse?
        try {
            val jsonReader = BufferedReader(InputStreamReader(file))

            val adapter = moshi.adapter(AeroplaneResponse::class.java)
            planeList = adapter.fromJson(jsonReader.readText())

            jsonReader.close()
            return Response.success(planeList!!)
        }
        catch (e: JsonEncodingException){
            val errorResponse =
                "{\n" +
                        "  \"type\": \"error\",\n" +
                        "  \"message\": \"What you were looking for isn't here.\"\n" +
                "}"
            val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
            return Response.error<AeroplaneResponse>(500, errorResponseBody)
        }
    }
}