package models.order

enum class OrderCommand {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    SEARCH
}