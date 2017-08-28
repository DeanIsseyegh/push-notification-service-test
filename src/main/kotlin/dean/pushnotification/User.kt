package dean.pushnotification

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.UniqueConstraint

@Entity data class User(@Column(unique = true) var username: String = "", var accessToken: String = "", @Id var id: Long? = null)