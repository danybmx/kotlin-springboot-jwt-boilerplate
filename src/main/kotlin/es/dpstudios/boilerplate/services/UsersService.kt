package es.dpstudios.boilerplate.services

import es.dpstudios.boilerplate.exceptions.UniqueViolationException
import es.dpstudios.boilerplate.models.User
import es.dpstudios.boilerplate.repositories.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
class UsersService(val usersRepository: UserRepository) {
    @GetMapping("/me")
    fun getCurrentUser(principal: Principal) : User = usersRepository.findByUsername(principal.name)!!

    @PostMapping("/register")
    fun register(@RequestBody user: User) : ResponseEntity<*> {
        user.password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.password)
        try {
            return ResponseEntity.ok().body(usersRepository.save(user))
        } catch (e: DataIntegrityViolationException) {
            throw UniqueViolationException("Username already in use", listOf("username"));
        }
    }
}
