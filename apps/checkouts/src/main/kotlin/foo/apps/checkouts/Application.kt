package foo.apps.checkouts

import io.micronaut.runtime.Micronaut

@OtherSpec
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
