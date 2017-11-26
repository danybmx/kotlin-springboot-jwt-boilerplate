package es.dpstudios.boilerplate.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ConstraintViolationException::class)
    fun invalidInput(ex: ConstraintViolationException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse()
        response.errorCode = "ValidationError"
        response.errorMessage = "Validation Failed"

        val errors = mutableMapOf<String, String>()
        if (ex.constraintViolations.isNotEmpty()) {
            ex.constraintViolations.forEach {
                errors.put(it.propertyPath.toString(), it.message)
            }
        }
        response.errors = errors;

        return ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UniqueViolationException::class)
    fun invalidInput(ex: UniqueViolationException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse()
        response.errorCode = "ValidationError"
        response.errorMessage = "Validation Failed"

        val errors = mutableMapOf<String, String>()
        if (ex.fields.isNotEmpty()) {
            ex.fields.forEach {
                errors.put(it, "should be unique")
            }
        }
        response.errors = errors;

        return ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST)
    }
}