package dean.pushnotification.domain

import dean.pushnotification.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.Optional
import javax.persistence.LockModeType

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun save(user: User): User
}