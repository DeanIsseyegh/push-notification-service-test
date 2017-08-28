package dean.pushnotification.domain

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class UserDTO(
        val username: String = "",
        val accessToken: String = "",
        val numOfNotificationsPushed: Int = 0,
        val creationTime: String = "") {

    fun toUser(): User {
        return User(username, accessToken, numOfNotificationsPushed)
    }

    fun toDTO(user: User): UserDTO {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return UserDTO(user.username, user.accessToken, user.numOfNotificationsPushed,
                dateFormat.format(user.creationTime))
    }

}