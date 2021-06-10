package com.micronaut.config

import com.micronaut.model.User
import com.micronaut.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import java.nio.file.attribute.UserPrincipalNotFoundException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword: AuthenticationProvider {

    @Inject
    lateinit var userRepository: UserRepository;

    override fun authenticate(
            httpRequest: HttpRequest<*>?,
            authenticationRequest: AuthenticationRequest<*,*>?
    ): Publisher<AuthenticationResponse> {
        var user: User? = null
        if (authenticationRequest != null) {
            user = userRepository.findByUserId(authenticationRequest.identity.toString())
        }
        if (user == null) {
            throw UserPrincipalNotFoundException("user not found")
        }
        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->
            if (authenticationRequest != null) {
                if (authenticationRequest.identity.toString().equals(user.userId) && authenticationRequest.secret.toString().equals(user.password)) {
                    val rolesStr: List<String> = user.roles?.map { it.roleName.toString() } ?: arrayListOf();
                    emitter.onNext(UserDetails(authenticationRequest.identity as String, rolesStr)) /*arrayListOf(user.role)*/
                    emitter.onComplete()
                } else {
                    emitter.onError(AuthenticationException(AuthenticationFailed()))
                }
            }
        }, BackpressureStrategy.ERROR)
    }
}