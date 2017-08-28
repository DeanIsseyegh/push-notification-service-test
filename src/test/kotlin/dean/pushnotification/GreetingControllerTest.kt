package dean.pushnotification

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.mock.mockito.MockBean



@RunWith(SpringRunner::class)
@SpringBootTest
class GreetingControllerTest {

    @Autowired
    private val greetingController: GreetingController? = null

    @Test fun getCounter() {
        val result = greetingController?.greeting("hey")
        Assert.assertThat(result.toString(), org.hamcrest.core.Is.`is`("User(username=Dean, accessToken=1234, id=1)"))
    }

    @Test fun greeting() {
    }

}