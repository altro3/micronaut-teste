package foo.apps.checkouts

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.openapi.annotation.OpenAPIGroup
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "carts")
@Controller("carts")
class CartController(
) {

  @Post(processes = [MediaType.APPLICATION_JSON])
  @ApiResponses(
      ApiResponse(
          responseCode = "201",
          content = [Content(schema = Schema(implementation = CartResponse::class))]),
      ApiResponse(
          responseCode = "400",
          content = [Content(schema = Schema(implementation = Error::class))]),
      ApiResponse(responseCode = "401"))
  fun createCart(
      @Body request: CartRequest,
  ): HttpResponse<*> {
      return HttpResponse.created(CartResponse(name = request.name, type = request.type, id = Math.random().toString().substring(0, 10)))
  }

  @Get(produces = [MediaType.APPLICATION_JSON])
  @ApiResponses(
      ApiResponse(
          responseCode = "200",
          content =
              [
                  Content(
                      array =
                          ArraySchema(
                              schema = Schema(implementation = CartResponse::class)))]),
      ApiResponse(
          responseCode = "404",
          content = [Content(schema = Schema(implementation = Error::class))]),
      ApiResponse(responseCode = "401"))
  @OpenAPIGroup("UI")
  fun getCart(@QueryValue id: String): HttpResponse<*> {
      return HttpResponse.ok(CartResponse(name = "my cart", type = "regular", id = id))
  }

}
