package dean.pushnotification.services

data class PushBulletResponse(var active: String = "",
                              var iden: String = "",
                              var created: Long = 0,
                              var modified: Long = 0,
                              var type: String = "",
                              var dismissed: String = "",
                              var direction: String = "",
                              var sender_iden: String = "",
                              var sender_email: String = "",
                              var sender_email_normalized: String = "",
                              var sender_name: String = "",
                              var receiver_iden: String = "",
                              var receiver_email: String = "",
                              var receiver_email_normalized: String = "",
                              var title: String = "",
                              var body: String = "")