package es.dpstudios.boilerplate.security.jwt

import es.dpstudios.boilerplate.models.RoleType
import es.dpstudios.boilerplate.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JWTUtils(@Value("\${jwt.expiration}") val expiration: Long,
               @Value("\${jwt.secret}") val secret: String,
               @Value("\${jwt.header}") val header: String,
               @Value("\${jwt.issuer}") val issuer: String) {

    private fun User.createJwt(): String {
        val claims = HashMap<String, Any>()
        claims.put("roles", this.roles!!.map { it.name }.joinToString(","))
        return Jwts.builder()
                .setIssuer(issuer)
                .setClaims(claims)
                .setSubject(this.username)
                .setExpiration(Date(Date().time + TimeUnit.HOURS.toMillis(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun addAuthentication(response: HttpServletResponse, user: User) {
        val jwt = user.createJwt()
        response.writer.write(jwt)
        response.writer.flush()
        response.writer.close()
    }

    fun getToken(request: HttpServletRequest): String {
        var token = request.getHeader(header) ?: ""
        token = token.replace("Bearer ", "")
        return token
    }

    fun getAuthentication(token: String): Authentication? {
        val tokenBody = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body

        val username = tokenBody.subject

        @Suppress("UNCHECKED_CAST")
        val roles = tokenBody["roles"].toString().split(",").map { RoleType.valueOf(it) }
        val res = roles.mapTo(LinkedList<GrantedAuthority>()) { SimpleGrantedAuthority(it.name) }

        return UsernamePasswordAuthenticationToken(username, null, res)
    }
}