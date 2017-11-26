package es.dpstudios.boilerplate.security

import es.dpstudios.boilerplate.models.User
import es.dpstudios.boilerplate.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailsService @Autowired
constructor(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(s: String): UserDetails {
        val u: User = userRepository.findByUsername(s) ?: throw UsernameNotFoundException("Username not found")
        return SecurityUserDetails(u)
    }
}