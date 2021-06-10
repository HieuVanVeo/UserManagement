package com.micronaut.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users")
class User(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var userId: String = "",
        var password: String = "",
        var username: String = "",
        var birthday: LocalDate = LocalDate.now(),
        var age: Int = 0,
        var marriage: Boolean = false,

        @ManyToMany(fetch = FetchType.LAZY)
        var roles: MutableSet<Role>? = mutableSetOf<Role>()

) {
//        private constructor() : this("","","", LocalDate.now(), 0, false)
}