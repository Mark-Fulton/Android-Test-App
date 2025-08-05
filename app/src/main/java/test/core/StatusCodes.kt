package test.core

import retrofit2.Response

enum class StatusCodes(val value: Int) {
    Ok(value = 200),
    BadRequest(value = 400),
    Unauthorised(value = 401),
    NotFound(value = 404),
    ServerIssue(value = 500)
}

fun <T> resolveStatusCodeWith(response: Response<T>): T {
    return when (response.code()) {
        StatusCodes.Ok.value -> response.body()
            ?: throw ServiceError.Error.Generic("Response returned null!")

        StatusCodes.BadRequest.value -> throw ServiceError.Error.BadRequest("A bad request occurred!")
        StatusCodes.Unauthorised.value -> throw ServiceError.Error.Unauthorised("An unauthorised error occurred!")
        StatusCodes.NotFound.value -> throw ServiceError.Error.NotFound("A not found error occurred!")
        StatusCodes.ServerIssue.value -> throw ServiceError.Error.ServerIssue("A server error occurred!")
        else -> throw ServiceError.Error.Generic("Error code not mapped: ${response.code()}")
    }
}
