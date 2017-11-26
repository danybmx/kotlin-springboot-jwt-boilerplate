package es.dpstudios.boilerplate.security.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import es.dpstudios.boilerplate.security.SecurityUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter internal constructor(s: String,
                                          authenticationManager: AuthenticationManager,
                                          val jwtUtils: JWTUtils) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(s)) {
    init {
        setAuthenticationManager(authenticationManager)
    }

    override fun attemptAuthentication(
            req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val cred: AccountCredentials? = jacksonObjectMapper().readValue(req.inputStream)

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        cred?.username,
                        cred?.password,
                        emptyList<GrantedAuthority>()
                )
        )
    }

    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain?, auth: Authentication) {
        jwtUtils.addAuthentication(res, (auth.principal as SecurityUserDetails).user)
    }
}