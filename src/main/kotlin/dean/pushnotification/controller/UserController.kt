package dean.pushnotification.controller

import dean.pushnotification.domain.UserDTO
import dean.pushnotification.domain.UserRepository
import dean.pushnotification.exception.UserAlreadyExistsException
import dean.pushnotification.services.DTOTransformService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class UserController @Autowired constructor(private val userRepository: UserRepository,
                                            private val dtoTransformService: DTOTransformService) {

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody userDTO: UserDTO): UserDTO {
        userRepository.findByUsername(userDTO.username).ifPresent { throw UserAlreadyExistsException(1, "Alredy exist") }
        val createdUser = userRepository.save(dtoTransformService.toUser(userDTO))
        return dtoTransformService.toDTO(createdUser)
    }

    @GetMapping("/users")
    fun getAllUsers(): List<UserDTO> {
        return dtoTransformService.toDTO(userRepository.findAll())
    }

    @GetMapping fun test() = "hey"

}