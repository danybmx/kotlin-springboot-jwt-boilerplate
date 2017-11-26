package es.dpstudios.boilerplate.repositories

import es.dpstudios.boilerplate.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}