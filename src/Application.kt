package com.zxventures

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.zxventures.dao.PdvCollection
import com.zxventures.service.PdvManager
import com.zxventures.service.PdvService
import io.ktor.jackson.*
import org.litote.kmongo.KMongo
import org.koin.dsl.module;
import org.koin.experimental.builder.singleBy
import org.koin.ktor.ext.Koin


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }


    if(!testing) {
        install(Koin) {
            modules(pdvAppModules)
        }
    }



    routing {
        pdvRoute()

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

val pdvAppModules = module {
    single { KMongo.createClient().getDatabase("pdvFinder") }
    single { PdvCollection(get()) }
    singleBy<PdvService, PdvManager>()
}

