package dean.pushnotification.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException

@ControllerAdvice
class ExceptionHandlingController {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun resourceAlreadyExists(ex: UserAlreadyExistsException) : ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse("Already Exists", ex.message)
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UserDoesNotExistException::class)
    fun userDoesNotExist(ex: UserDoesNotExistException) : ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse("User does not exist", ex.message)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun httpClientError(ex: HttpClientErrorException) : ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse("There was an error with the push request to push bullet", ex.message)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}