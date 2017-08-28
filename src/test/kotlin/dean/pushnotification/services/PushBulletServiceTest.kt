package dean.pushnotification.services

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.test.context.junit4.SpringRunner
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner::class)
@SpringBootTest
class PushBulletServiceTest {

    @Autowired private var pushBulletService: PushBulletService? = null
    @MockBean private var restTemplateBuilder: RestTemplateBuilder? = null

    @Test
    fun sendsPushBulletRequest() {
        val restTemplate = mock(RestTemplate::class.java)
        `when`(restTemplateBuilder?.build()).thenReturn(restTemplate)
        val pushBulletRequest = PushBulletRequest("x", "y", "z")

        pushBulletService?.sendPush("1234", pushBulletRequest)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Access-Token", "1234")
        var httpEntity = HttpEntity<PushBulletRequest>(pushBulletRequest, headers)

        verify(restTemplate, times(1))
                .postForObject(pushBulletService?.URL, httpEntity, String::class.java)
    }

}