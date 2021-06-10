package com.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
//import io.micronaut.security.annotation.Secured

@Controller("api/home")
class HomeController {

    @Get("/")
    fun home(): String {
        return "hello"
    }
}