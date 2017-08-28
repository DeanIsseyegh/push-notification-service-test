package dean.pushnotification.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlingController {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun resourceAlreadyExists(ex: UserAlreadyExistsException) : ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse("Already Exists", ex.message)
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }
}