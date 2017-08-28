package dean.pushnotification.domain

import org.springframework.context.annotation.Bean
import java.text.SimpleDateFormat

data class UserDTO(
        val username: String = "",
        val accessToken: String = "",
        val numOfNotificationsPushed: Int = 0,
        val creationTime: String = "") {

    fun toUser(): User {
        return User(username, accessToken, numOfNotificationsPushed)
    }

}