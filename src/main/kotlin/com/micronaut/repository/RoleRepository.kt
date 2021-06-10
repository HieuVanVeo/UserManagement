package com.micronaut.repository

import com.micronaut.model.Role
import com.micronaut.model.RoleName
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface RoleRepository: CrudRepository<Role, Long> {
    fun findByRoleName(roleName: RoleName): Role
}