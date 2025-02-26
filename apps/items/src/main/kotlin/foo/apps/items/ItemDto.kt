package foo.apps.items

data class ItemRequest (
    val name: String,
    val upc: String,
)

data class ItemResponse (
    val name: String,
    val upc: String,
    val id: String
)
