package es.dpstudios.boilerplate.exceptions

class ExceptionResponse {
    var errorCode: String? = null
    var errorMessage: String? = null
    var errors: Map<String, String>? = null
}