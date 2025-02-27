package foo.apps.items

import foo.libs.common.OpenApiSpec
import io.micronaut.runtime.Micronaut

@OpenApiSpec
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
