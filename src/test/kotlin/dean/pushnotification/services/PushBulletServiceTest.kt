package dean.pushnotification.services

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserRepository
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
    @MockBean private var userRepository: UserRepository? = null

    @Test
    fun sendsPushBulletRequestAndReturnsResponse() {
        val restTemplate = mock(RestTemplate::class.java)
        `when`(restTemplateBuilder?.build()).thenReturn(restTemplate)
        val pushBulletRequest = PushBulletRequest("x", "y", "z")
        val expectedResponse = PushBulletResponse()
        val httpEntity = createHttpEntity(pushBulletRequest)
        stubRestTemplatePost(restTemplate, httpEntity, expectedResponse)
        val response = pushBulletService?.sendPush("1234", pushBulletRequest)
        verifyRequestOnlySentOnce(restTemplate, httpEntity)
        assertThat(response, `is`(expectedResponse))
    }

    @Test
    fun incrementsUserCount() {
        val user = mock(User::class.java)
        pushBulletService?.incrementCounter(user)
        verify(user, times(1)).incrementCounter()
        verify(userRepository, times(1))?.save(user)
    }

    fun stubRestTemplatePost(restTemplate: RestTemplate, httpEntity: HttpEntity<PushBulletRequest>, expectedResponse: PushBulletResponse) {
        `when`(restTemplate
                .postForObject(pushBulletService?.URL,
                        httpEntity,
                        PushBulletResponse::class.java))
                .thenReturn(expectedResponse)
    }

    fun verifyRequestOnlySentOnce(restTemplate: RestTemplate, httpEntity: HttpEntity<PushBulletRequest>) {
        verify(restTemplate, times(1))
                .postForObject(pushBulletService?.URL,
                        httpEntity,
                        PushBulletResponse::class.java)
    }

    fun createHttpEntity(pushBulletRequest: PushBulletRequest): HttpEntity<PushBulletRequest> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Access-Token", "1234")
        return HttpEntity(pushBulletRequest, headers)
    }

}