package es.dpstudios.boilerplate.config

import es.dpstudios.boilerplate.security.jwt.JWTAuthenticationEntryPoint
import es.dpstudios.boilerplate.security.jwt.JWTAuthenticationFilter
import es.dpstudios.boilerplate.security.jwt.JWTLoginFilter
import es.dpstudios.boilerplate.security.jwt.JWTUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var jwtUtils: JWTUtils

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(JWTAuthenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/logout", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                // Filter the api/login requests
                .addFilterBefore(JWTLoginFilter("/login", authenticationManager(), jwtUtils),
                        UsernamePasswordAuthenticationFilter::class.java)
                // Filter the presence of JWT token in header
                .addFilterBefore(JWTAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter::class.java)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}