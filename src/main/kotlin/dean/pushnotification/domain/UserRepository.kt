package dean.pushnotification.domain

import dean.pushnotification.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findById(id: Long?): Optional<User>
    fun findByUsername(username: String): Optional<User>
}