package es.dpstudios.boilerplate.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @get: NotBlank
        @Column(unique = true)
        var username: String? = null,

        @JsonIgnore
        var password: String? = null,

        @get: NotBlank
        var name: String? = null,

        @get: NotEmpty
        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "user_role", joinColumns = (arrayOf(JoinColumn(name = "user_id"))))
        var roles: MutableSet<RoleType>? = mutableSetOf(RoleType.USER)

)

