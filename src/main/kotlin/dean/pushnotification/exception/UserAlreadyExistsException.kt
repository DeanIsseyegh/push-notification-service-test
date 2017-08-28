package dean.pushnotification.exception

class UserAlreadyExistsException(userId: Long, msg: String) : RuntimeException(msg) {

}