package dean.pushnotification.controller

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserDTO
import dean.pushnotification.domain.UserRepository
import dean.pushnotification.exception.UserAlreadyExistsException
import dean.pushnotification.exception.UserDoesNotExistException
import dean.pushnotification.services.DTOTransformService
import dean.pushnotification.services.PushBulletRequest
import dean.pushnotification.services.PushBulletResponse
import dean.pushnotification.services.PushBulletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class UserController @Autowired constructor(private val userRepository: UserRepository,
                                            private val dtoTransformService: DTOTransformService,
                                            private val pushBulletService: PushBulletService) {

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

    @PatchMapping("/users/{id}/push")
    @ResponseStatus(HttpStatus.OK)
    fun push(@RequestBody pushRequest: PushRequest, @PathVariable id: Long?) : PushBulletResponse? {
        val user = userRepository.findByUsername(pushRequest.username)
        user.orElseThrow { throw UserDoesNotExistException("User ${pushRequest.username} not found") }
        return sendPushAndIncrementCounter(user.get(), PushBulletRequest(pushRequest.body, pushRequest.title, pushRequest.type))
    }

    private fun sendPushAndIncrementCounter(user: User, pushBulletRequest: PushBulletRequest) : PushBulletResponse? {
        val response = pushBulletService.sendPush(user.accessToken, pushBulletRequest)
        pushBulletService.incrementCounter(user)
        return response
    }

}