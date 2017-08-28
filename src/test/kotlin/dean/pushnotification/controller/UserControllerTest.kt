package dean.pushnotification.controller

import dean.pushnotification.domain.User
import dean.pushnotification.domain.UserDTO
import dean.pushnotification.domain.UserRepository
import dean.pushnotification.exception.UserAlreadyExistsException
import dean.pushnotification.exception.UserDoesNotExistException
import dean.pushnotification.services.DTOTransformService
import dean.pushnotification.services.PushBulletRequest
import dean.pushnotification.services.PushBulletService
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.HttpClientErrorException
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest {

    @Autowired private val userController: UserController? = null
    @MockBean private val dtoTransformerService: DTOTransformService? = null
    @MockBean private val userRepository: UserRepository? = null
    @MockBean private val pushBulletService: PushBulletService? = null

    @Test
    fun registersAUserWithNameAndToken() {
        val userDTO = mock(UserDTO::class.java)
        val user = mock(User::class.java)
        `when`(dtoTransformerService?.toUser(userDTO)).thenReturn(user)
        `when`(userRepository?.save(user)).thenReturn(user)
        `when`(dtoTransformerService?.toDTO(user)).thenReturn(userDTO)

        val response = userController?.register(userDTO)
        assertThat(response, `is`(userDTO))
    }

    @Test(expected = UserAlreadyExistsException::class)
    fun throwsExceptionIfUserAlreadyExists() {
        val userDTO = UserDTO("dean")
        `when`(userRepository?.findByUsername("dean")).thenReturn(Optional.of(User()))
        userController?.register(userDTO)
    }

    @Test
    fun returnsAllUsers() {
        val userDTOs = listOf(UserDTO())
        val users = listOf(mock(User::class.java))
        `when`(userRepository?.findAll()).thenReturn(users)
        `when`(dtoTransformerService?.toDTO(users)).thenReturn(userDTOs)
        val response = userController?.getAllUsers()
        assertThat(response, `is`(userDTOs))
    }

    @Test
    fun givenNoUsersReturnEmptyList() {
        `when`(userRepository?.findAll()).thenReturn(listOf())
        val response = userController?.getAllUsers()
        assertThat(response, `is`(listOf()))
    }

    @Test(expected = UserDoesNotExistException::class)
    fun givenUserDoesNotExistThenThrowException() {
        val pushRequest = PushRequest("username", "body", "title", "type")
        userController?.push(pushRequest, 1)
    }

    @Test
    fun givenUserExistsThenSendPushBulletRequest() {
        val pushRequest = PushRequest("username", "body", "title", "type")
        val user = User("username", "1234")
        `when`(userRepository?.findByUsername("username")).thenReturn(Optional.of(user))
        userController?.push(pushRequest, 1)
        val pushBulletRequest = PushBulletRequest("body", "title", "type")
        verify(pushBulletService, times(1))?.sendPush("1234", pushBulletRequest)
        verify(pushBulletService, times(1))?.incrementCounter(user)
    }

    @Test
    fun givenUserExistsButThereIsAPushBulletExceptionThenThrowExceptionAndDoNotIncrement() {
        val pushRequest = PushRequest("username", "body", "title", "type")
        val user = User("username", "1234")
        `when`(userRepository?.findByUsername("username")).thenReturn(Optional.of(user))
        val pushBulletRequest = PushBulletRequest("body", "title", "type")
        `when`(pushBulletService?.sendPush("1234", pushBulletRequest))
                .thenThrow(HttpClientErrorException::class.java)
        try {
            userController?.push(pushRequest, 1)
            assertTrue(false)
        } catch (ex: HttpClientErrorException) {
            verify(pushBulletService, times(0))?.incrementCounter(user)
        }
    }
}