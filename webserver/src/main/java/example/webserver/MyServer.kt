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
  fun familyNew(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "family_name") name: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        return null
      }
    }

    val family = Family(id, password, name)
    familiesJson.families.add(family)
    return family
  }

  @PostMapping("/family/change-family")
  fun familyChange(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "new_family_password") new_family_password: String?,
    @RequestParam(value = "new_family_name") new_family_name: String?,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        if (new_family_password != null) {
          family.password = new_family_password
        }

        if (new_family_name != null) {
          family.name = new_family_name
        }

        return family
      }
    }

    return null
  }

  @PostMapping("/family/get-family")
  fun familyGet(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        return family
      }
    }

    return null
  }

  @PostMapping("/user/new-user")
  fun userNew(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "family_name") name: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        return null
      }
    }

    val family = Family(id, password, name)
    familiesJson.families.add(family)
    return family
  }

  @PostMapping("/user/change-user")
  fun userChange(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
    @RequestParam(value = "new_family_password") new_family_password: String?,
    @RequestParam(value = "new_family_name") new_family_name: String?,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        if (new_family_password != null) {
          family.password = new_family_password
        }

        if (new_family_name != null) {
          family.name = new_family_name
        }

        return family
      }
    }

    return null
  }

  @PostMapping("/user/get-user")
  fun userGet(
    @RequestParam(value = "family_id") id: Int,
    @RequestParam(value = "family_password") password: String,
  ): Family? {
    val familiesJson: FamiliesJson = mapper.readValue(File(FamiliesJsonFile), FamiliesJsonInstance.javaClass)

    for (family in familiesJson.families) {
      if (family.id == id && family.password == password) {
        return family
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
