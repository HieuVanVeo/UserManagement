package com.micronaut.model

import java.time.LocalDate
import javax.validation.constraints.NotBlank

class RegisterForm(
        var userId: String = "",
        var password: String = "",
        var username: String = "",
        var birthday: LocalDate = LocalDate.now(),
        var age: Int = 0,
        var marriage: Boolean = false,
        var role: MutableSet<String>
) {
}