package com.micronaut.controller

import com.micronaut.model.RegisterForm
import com.micronaut.model.Role
import com.micronaut.model.RoleName
import com.micronaut.model.RoleName.*
import com.micronaut.model.User
import com.micronaut.repository.RoleRepository
import com.micronaut.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.validation.Valid

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("api/users")
class UserController {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var roleRepository: RoleRepository

//    @Post("/register")
//    fun addUser(@Valid @Body user: User): User {
//
//        var listrole: Set<Role> = user.roles;
//        var roles: Set<Role> = setOf()
//
//        listrole.forEach {
//            when(it.roleName) {
//                USER -> {
//                    var role: Role = roleRepository.findByRoleName(USER);
//                    roles.plus(role); }
//
//                ADMIN -> {
//                    var role: Role = roleRepository.findByRoleName(ADMIN);
//                    roles.plus(role);
//                }
//            }
//        }
//
//        user.roles = roles;
//        return userRepository.save(user)
//    }

    @PermitAll
    @Post("/register")
    fun addUser(@Body registerForm: RegisterForm): HttpResponse<User> {

        var listrole: Set<String> = registerForm.role;
        var roles: MutableSet<Role> = mutableSetOf();

        listrole.forEach {
            when(it) {
                "USER" -> {
                    var role: Role = roleRepository.findByRoleName(USER);
                    roles.add(role); }

                "ADMIN" -> {
                    var role: Role = roleRepository.findByRoleName(ADMIN);
                    roles.add(role);
                }
            }
        }
        var user = User();
        user.userId = registerForm.userId;
        user.password = registerForm.password;
        user.age = registerForm.age;
        user.birthday = registerForm.birthday;
        user.marriage = registerForm.marriage;
        user.username = registerForm.username;
        user.roles = roles;
        return HttpResponse.status<Any?>(HttpStatus.OK).body(userRepository.save(user))
    }

    @Secured("USER")
    @Get("/find_all")
    fun findAllUser() = userRepository.findAll()

    @Secured("USER")
    @Get(("/find_all/{id}"))
    fun findUserById(id: Long) : HttpResponse<User> {
        var user = userRepository.findById(id);
        if(user.isPresent) {
            return HttpResponse.status<Any?>(HttpStatus.OK).body(user.get());
        }
        return HttpResponse.status<Any?>(HttpStatus.NOT_FOUND).body(null);
    }

    @Secured("USER")
    @Put("/update/{id}")
    fun updateUser(@PathVariable id: Long, @Body user: User): HttpResponse<User> {
        var userData = userRepository.findById(id);
        if(userData.isPresent) {
            var user_ = userData.get();
            user_.userId = user.userId;
            user_.password = user.password;
            user_.age = user.age;
            user_.birthday = user.birthday;
            user_.marriage = user.marriage;
            user_.username = user.username;
            return HttpResponse.status<Any?>(HttpStatus.OK).body(userRepository.update(user_));
        }
        else {
            return HttpResponse.status<Any?>(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Secured("USER")
    @Delete("delete/{id}")
    fun deleteUser(id: Long): HttpResponse<User> {
        userRepository.deleteById(id);
        return HttpResponse.status<Any?>(HttpStatus.OK).body(null);
    }
}