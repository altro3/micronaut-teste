package foo.apps.checkouts

data class CartRequest (
    val name: String,
    val type: String,
)

data class CartResponse (
    val name: String,
    val type: String,
    val id: String
)
