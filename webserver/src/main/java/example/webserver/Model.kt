package example.webserver

data class FamiliesJson(
  val families: MutableList<Family>,
)

data class Family(
  var name: String,
  var password: String,
  var namePretty: String,
  var users: MutableList<String>,
)

data class UsersJson(
  val users: MutableList<User>,
)

data class User(
  var uuid: String,
  var password: String,
  var namePretty: String,
  var type: Int, // TODO: "enum"
  var familyName: String,
  var email: String?,
)

data class Tasks(
  var uuid: String,
  var id: Int,
  var title: String,
)
