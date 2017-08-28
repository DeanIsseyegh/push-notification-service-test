package dean.pushnotification.services

import dean.pushnotification.controller.UserController
import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserDTO
import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.*
import java.util.Calendar



@RunWith(SpringRunner::class)
@SpringBootTest
class DTOTransformServiceTest {

    @Autowired
    private val service: DTOTransformService? = null

    @Test
    fun convertsUserToDTO() {
        val date = mock(Date::class.java)
        `when`(date.time).thenReturn(1503944480666L)
        val user = User("dean", "1234", 1, date)
        assertThat(service?.toDTO(user), `is`(UserDTO("dean", "1234", 1, "2017-08-28T19:21:20")))
    }

    @Test
    fun convertsListOfUsersToListOfDTOs() {
        val date = mock(Date::class.java)
        `when`(date.time).thenReturn(1503944480666L)

        val user1 = User("dean", "1234", 1, date)
        val user2 = User("bob", "9876", 4, date)
        val users = listOf(user1, user2)

        val expected = listOf(UserDTO("dean", "1234", 1, "2017-08-28T19:21:20"),
                UserDTO("bob", "9876", 4, "2017-08-28T19:21:20"))
        assertThat(service?.toDTO(users), `is`(expected))
    }

    @Test
    fun convertsDTOToUser() {
        val dto = UserDTO("dean", "1234", 1)
        assertThat(service?.toUser(dto), `is`(User("dean", "1234", 1)))
    }

}