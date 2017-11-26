package es.dpstudios.boilerplate.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JWTAuthenticationFilter(val utils : JWTUtils) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val token = utils.getToken(request as HttpServletRequest)
        if ("" != token) {
            val authentication = utils.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}