package foo.libs.common

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info =
        Info(
            title = "pppp",
            version = "\${api.version}",
            description = "\${openapi.description}"))
annotation class OpenApiSpec
