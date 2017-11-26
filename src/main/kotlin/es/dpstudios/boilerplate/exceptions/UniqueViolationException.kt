package es.dpstudios.boilerplate.exceptions

import javax.validation.ValidationException

class UniqueViolationException(override var message: String, var fields: List<String>) : ValidationException()