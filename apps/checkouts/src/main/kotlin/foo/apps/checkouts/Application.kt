package foo.apps.checkouts

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
  info =
    Info(
      title = "\${api.title}",
      version = "\${api.version}",
      description = "\${openapi.description}"),
 )
object Application {
  fun main(args: Array<String>) {
    Micronaut.build()
      .banner(false)
      .packages("foo")
      .mainClass(Application::class.java)
      .args(*args)
      .start()
  }
}
