package org.example

import com.sun.org.apache.xerces.internal.util.URI
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.module() {
    install(ContentNegotiation) {
        json(Json { prettyPrint = true; isLenient = true })  // Configure kotlinx serialization
    }

 //   val databaseUrl = System.getenv("DATABASE_URL")

    Database.connect(
        url = "jdbc:postgresql://products68.flycast:5432/products_purple_morning_67?sslmode=disable",
        driver = "org.postgresql.Driver",
        user = "products_purple_morning_67",
        password = "eatYdIVmKJKZz2Y"
    )

    /*   Database.connect(
           url = "jdbc:postgresql://localhost:5432/your_database",
           driver = "org.postgresql.Driver",
           user = "your_user",
           password = "your_password"
       )*/

    transaction {
        SchemaUtils.create(Users, Products)
    }

    routing {
        login()
        addProduct()
        getProduct()
        updateUserAmount()
        getUserAmount()
    }
}
