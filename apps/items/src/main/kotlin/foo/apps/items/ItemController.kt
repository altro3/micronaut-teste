package foo.apps.items

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

@Tag(name = "items")
@Controller("items")
class ItemController(
) {

  @Post(processes = [MediaType.APPLICATION_JSON])
  @ApiResponses(
      ApiResponse(
          responseCode = "201",
          content = [Content(schema = Schema(implementation = ItemResponse::class))]),
      ApiResponse(
          responseCode = "400",
          content = [Content(schema = Schema(implementation = Error::class))]),
      ApiResponse(responseCode = "401"))
  suspend fun addItem(
      @Body request: ItemRequest,
  ): HttpResponse<*> {
      return HttpResponse.created(ItemResponse(name = request.name, upc = request.upc, id = Math.random().toString().substring(0, 10)))
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
                              schema = Schema(implementation = ItemResponse::class)))]),
      ApiResponse(
          responseCode = "404",
          content = [Content(schema = Schema(implementation = Error::class))]),
      ApiResponse(responseCode = "401"))
  @OpenAPIGroup("UI")
  suspend fun getItems(@QueryValue id: String): HttpResponse<*> {
      return HttpResponse.ok(ItemResponse(name = "my cart", upc = "88989898", id = id))
  }

}
