package es.dpstudios.boilerplate.security.jwt

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        response!!.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
    }

}