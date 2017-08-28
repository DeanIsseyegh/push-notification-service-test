package dean.pushnotification.controller

data class PushRequest(var username: String = "", var body: String = "", var title: String = "", var type: String = "")