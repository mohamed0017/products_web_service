package org.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// Login API: Create a user with a unique ID and name
fun Route.login() {
    post("/login") {
        val userName = call.receive<String>()
        val user = transaction {
            Users.insertAndGetId { it[name] = userName }
        }
        call.respond(HttpStatusCode.Created, User(user.value, userName, 0.0))
    }
}

// Add Product API: Add a new product
fun Route.addProduct() {
    post("/product") {
        val product = call.receive<Product>()
        transaction {
            Products.insert {
                it[name] = product.name
                it[price] = product.price
            }
        }
        call.respond(HttpStatusCode.Created, product)
    }
}

// Get Product Details API: Retrieve product details by ID
fun Route.getProduct() {
    get("/product/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        val product = transaction {
            Products
                .select { Products.id eq id }
                .map { Product(it[Products.id].value, it[Products.name], it[Products.price]) }
                .singleOrNull()
        }
        if (product != null) {
            call.respond(product)
        } else {
            call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }
}

// Update User Amount API: Update the user's total amount
fun Route.updateUserAmount() {
    put("/user/{id}/amount") {
        val userId = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        val newAmount = call.receive<ProductRequestBody>()
        val updatedRows = transaction {
            Users.update({ Users.id eq userId }) { it[amount] = newAmount.amount }
        }
        if (updatedRows > 0) {
            call.respond(HttpStatusCode.OK, "Amount updated")
        } else {
            call.respond(HttpStatusCode.NotFound, "User not found")
        }
    }
}

// Get User Amount API: Retrieve the user's total amount
fun Route.getUserAmount() {
    get("/user/{id}/amount") {
        val userId = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
        val user = transaction {
            Users
                .select { Users.id eq userId }
                .map { User(it[Users.id].value, it[Users.name], it[Users.amount]) }
                .singleOrNull()
        }
        if (user != null) {
            call.respond(user.amount)
        } else {
            call.respond(HttpStatusCode.NotFound, "User not found")
        }
    }
}




