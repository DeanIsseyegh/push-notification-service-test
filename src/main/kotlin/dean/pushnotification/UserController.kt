package dean.pushnotification

import dean.pushnotification.domain.UserDTO
import dean.pushnotification.domain.UserRepository
import dean.pushnotification.exception.UserAlreadyExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class UserController @Autowired constructor(private val userRepository: UserRepository) {

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody userDTO: UserDTO): UserDTO {
        userRepository.findByUsername(userDTO.username).ifPresent { throw UserAlreadyExistsException(1, "Alredy exist") }
        val createdUser = userRepository.save(userDTO.toUser())
        return userDTO.toDTO(createdUser)
    }

    @GetMapping fun test() = "hey"

}