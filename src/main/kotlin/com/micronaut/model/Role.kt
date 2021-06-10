package com.micronaut.model

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,

        @Enumerated(EnumType.STRING)
        @NaturalId
        var roleName: RoleName = RoleName.USER,

) {
}