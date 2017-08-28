package dean.pushnotification.services

import dean.pushnotification.controller.PushRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class PushBulletService {

    val URL: String = "https://api.pushbullet.com/v2/pushes"

    @Autowired
    var restTemplateBuilder: RestTemplateBuilder? = null

    fun sendPush(accessToken: String, pushBulletRequest: PushBulletRequest) {
        val restTemplate = restTemplateBuilder?.build()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Access-Token", accessToken)
        var httpEntity = HttpEntity<PushBulletRequest>(pushBulletRequest, headers)
        restTemplate?.postForObject(URL, httpEntity, String::class.java)
    }

}

/*

@GetMapping("/pushtest")
fun testie() {
    val user = userRepository.findByUsername("dean").ifPresent {
        val username = it.username
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Access-Token", "o.WvLYaN7NXchwTkCVPsNxiFGAfQvscngr")
        var maRequest = MaRequest("Space Ele", "YO", "note")
        var httpEntity = HttpEntity<MaRequest>(maRequest, headers)
        restTemplate.postForObject(BASE_URL + "pushes", httpEntity, String::class.java)

    }

}

}

data class MaRequest(var body: String, var title: String, var type: String)*/
