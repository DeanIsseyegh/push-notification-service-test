package dean.pushnotification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController @Autowired constructor(private val userRepository: UserRepository) {

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            userRepository.save( User("Dean", "1234", 1))
            var user = userRepository.findByUsername("Dean")

}