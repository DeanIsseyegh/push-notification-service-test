package dean.pushnotification.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import javax.persistence.TemporalType.TIMESTAMP

@EntityListeners(AuditingEntityListener::class)
@Entity data class User(
        @Column(unique = true) var username: String = "",
        var accessToken: String = "",
        var numOfNotificationsPushed: Int = 0,
        @CreatedDate @Temporal(TIMESTAMP) var creationTime: Date? = null,
        @GeneratedValue @Id var id: Long? = null)