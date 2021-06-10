package com.micronaut.repository

import com.micronaut.model.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User
    fun findByUserId(username: String): User?
}