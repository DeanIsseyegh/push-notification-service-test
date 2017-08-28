package dean.pushnotification

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserDTO
import dean.pushnotification.domain.UserRepository
import dean.pushnotification.exception.UserAlreadyExistsException
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private val userController: UserController? = null

    @MockBean
    private val userRepository: UserRepository? = null

    @Test
    fun registersAUserWithNameAndToken() {
        val userDTO = mock(UserDTO::class.java)
        val user = mock(User::class.java)
        `when`(userDTO.toUser()).thenReturn(user)
        `when`(userRepository?.save(user)).thenReturn(user)
        `when`(userDTO.toDTO(user)).thenReturn(userDTO)

        val response = userController?.register(userDTO)
        assertThat(response, `is`(userDTO))
    }

    @Test(expected = UserAlreadyExistsException::class)
    fun throwsExceptionIfUserAlreadyExists() {
        val userDTO = UserDTO("dean")
        `when`(userRepository?.findByUsername("dean")).thenReturn(Optional.of(User()))
        userController?.register(userDTO)
    }

}