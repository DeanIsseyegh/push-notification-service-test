package dean.pushnotification.services

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserDTO
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class DTOTransformService {

  /*  fun toUser(): User {
        return User(username, accessToken, numOfNotificationsPushed)
    }*/

    fun toDTO(user: User): UserDTO {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return UserDTO(user.username, user.accessToken, user.numOfNotificationsPushed,
                dateFormat.format(user.creationTime))
    }

    fun toDTO(users: List<User>): List<UserDTO> = users.map { toDTO(it) }

    fun toUser(dto: UserDTO): User {
        return User(dto.username, dto.accessToken, dto.numOfNotificationsPushed)
    }

}