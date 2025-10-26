package example.webserver

data class FamiliesJson(
  val families: MutableList<Family>,
)

data class Family(
  val id: Int,
  var password: String,
  var name: String,
)

data class UsersJson(
  val users: MutableList<User>,
)

data class User(
  val user_id: Int,
  val family_id: Int,
  val user_email: String,
  val user_password: String,
  val user_name: String,
  val user_type: Int, // TODO: "enum"
)

data class Tasks(
  val id: Int,
  val title: String,
)
