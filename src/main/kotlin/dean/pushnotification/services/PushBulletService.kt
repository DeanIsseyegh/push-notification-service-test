package dean.pushnotification.services

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.data.jpa.repository.Lock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import javax.persistence.LockModeType
import javax.persistence.LockModeType.PESSIMISTIC_WRITE

@Service
class PushBulletService @Autowired constructor(private val userRepository: UserRepository) {

    val URL: String = "https://api.pushbullet.com/v2/pushes"

    @Autowired
    var restTemplateBuilder: RestTemplateBuilder? = null

    fun sendPush(accessToken: String, pushBulletRequest: PushBulletRequest) : PushBulletResponse? {
        val restTemplate = restTemplateBuilder?.build()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Access-Token", accessToken)
        return restTemplate?.postForObject(URL,
                HttpEntity<PushBulletRequest>(pushBulletRequest, headers),
                PushBulletResponse::class.java)
    }

    fun incrementCounter(user: User) {
        user.incrementCounter()
        userRepository.save(user)
    }

}
