package org.example

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(val id: Int, val name: String, var amount: Double)
@Serializable
data class Product(val id: Int, val name: String, val price: Double)
@Serializable
data class ProductRequestBody(val amount: Double)

// Database tables
object Users : IntIdTable() {
    val name = varchar("name", 100)
    val amount = double("amount").default(0.0)
}

object Products : IntIdTable() {
    val name = varchar("name", 100)
    val price = double("price")
}
