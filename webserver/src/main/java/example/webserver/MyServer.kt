package example.webserver

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

const val FamiliesJsonFile = "./families.json"
const val UsersJsonFile = "./users.json"

val FamiliesJsonInstance = FamiliesJson(families = emptyList<Family>().toMutableList())
val UsersJsonInstance = UsersJson(users = emptyList<User>().toMutableList())

val mapper: ObjectMapper = ObjectMapper()
  .findAndRegisterModules()
  .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

@SpringBootApplication
@RestController
class MyServer {
  @PostMapping("/family/new-family")
  fun newFamily(
    @RequestParam(value = "family_name") name: String,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "family_name_pretty") namePretty: String,
  ): Family? {
    if (name.isEmpty() || name[0].isDigit() || !name.all { it.isLetterOrDigit() || it in "-_" }) {
      return null
    }

    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.name == name && family.password == password) {
        return null
      }
    }

    val family = Family(name, password, namePretty, mutableListOf())
    familiesJson.families.add(family)
    return family
  }

  @PostMapping("/family/update-family")
  fun updateFamily(
    @RequestParam(value = "family_name") name: String,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "new_family_password") newPassword: String?,
    @RequestParam(value = "new_family_name_pretty") newNamePretty: String?,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.name == name && family.password == password) {
        if (newPassword != null) {
          family.password = newPassword
        }

        if (newNamePretty != null) {
          family.name = newNamePretty
        }

        return family
      }
    }

    return null
  }

  @PostMapping("/family/add-user")
  fun addFamilyUser(
    @RequestParam(value = "family_name") name: String,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "new_user_uuid") newUserUuid: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.name == name && family.password == password) {
        if (family.users.contains(newUserUuid)) {
          return null
        }

        family.users.add(newUserUuid)
        return family
      }
    }

    return null
  }

  @PostMapping("/family/remove-user")
  fun removeFamilyUser(
    @RequestParam(value = "family_name") name: String,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "removed_user_uuid") removedUserUuid: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.name == name && family.password == password) {
        val idx = family.users.indexOf(removedUserUuid)
        if (idx == -1) {
          return null
        }

        family.users.remove(removedUserUuid)
        return family
      }
    }

    return null
  }

  @PostMapping("/family/get-family")
  fun getFamily(
    @RequestParam(value = "family_name") name: String,
    @RequestParam(value = "family_password") password: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.name == name && family.password == password) {
        return family
      }
    }

    return null
  }

  @PostMapping("/user/new-user")
  fun newUser(
    @RequestParam(value = "user_uuid") uuid: String,
    @RequestParam(value = "user_password") password: String,
    @RequestParam(value = "user_name_pretty") namePretty: String,
    @RequestParam(value = "user_email") email: String?,
    @RequestParam(value = "user_type") type: Int,
    @RequestParam(value = "family_name") familyName: String,
  ): User? {
    val usersJson: UsersJson = mapper.readValue(File(UsersJsonFile), UsersJsonInstance.javaClass)

    for (user in usersJson.users) {
      if (user.uuid == uuid && user.password == password) {
        return null
      }
    }

    val user = User(uuid, password, namePretty, type, familyName, email)
    usersJson.users.add(user)
    return user
  }

  @PostMapping("/user/update-user")
  fun updateUser(
    @RequestParam(value = "user_name") name: String,
    @RequestParam(value = "user_password") password: String,
    @RequestParam(value = "new_user_password") newPassword: String?,
    @RequestParam(value = "new_user_name_pretty") newNamePretty: String?,
    @RequestParam(value = "new_user_type") newType: Int?,
    @RequestParam(value = "new_family_name") newFamilyName: String?,
    @RequestParam(value = "new_user_email") newEmail: String?,
  ): User? {
    val usersJson: UsersJson = mapper.readValue(File(UsersJsonFile), UsersJsonInstance.javaClass)

    for (user in usersJson.users) {
      if (user.uuid == name && user.password == password) {
        if (newPassword != null) {
          user.password = newPassword
        }

        if (newNamePretty != null) {
          user.namePretty = newNamePretty
        }

        if (newType != null) {
          user.type = newType
        }

        if (newFamilyName != null) {
          user.familyName = newFamilyName
        }

        if (newEmail != null) {
          user.email = newEmail
        }

        return user
      }
    }

    return null
  }

  @PostMapping("/user/get-user")
  fun getUser(
    @RequestParam(value = "user_name") name: String,
    @RequestParam(value = "user_password") password: String,
  ): User? {
    val usersJson: UsersJson = mapper.readValue(File(UsersJsonFile), UsersJsonInstance.javaClass)

    for (user in usersJson.users) {
      if (user.uuid == name && user.password == password) {
        return user
      }
    }

    return null
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(MyServer::class.java, *args)
    }
  }
}
