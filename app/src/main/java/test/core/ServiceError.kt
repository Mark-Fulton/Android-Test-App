package test.core

sealed class ServiceError {
    sealed class Error(error: String) : Exception(error) {
        class Generic(error: String) : Error(error)
        class NotFound(error: String) : Error(error)
        class BadRequest(error: String) : Error(error)
        class ServerIssue(error: String) : Error(error)
        class Unauthorised(error: String) : Error(error)
    }
}