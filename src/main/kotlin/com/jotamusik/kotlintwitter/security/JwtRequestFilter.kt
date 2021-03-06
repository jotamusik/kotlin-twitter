package com.jotamusik.kotlintwitter.security

import com.jotamusik.kotlintwitter.JwtUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
		private val jwtUserDetailsService: JwtUserDetailsService,
		private val jwtTokenUtil: JwtTokenUtil,
) : OncePerRequestFilter() {

	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

		val requestTokenHeader: String = request.getHeader("Authorization")

		var username: String = String()
		var jwtToken: String = String()
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7)
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken)
			} catch (e: IllegalArgumentException ) {
				println("Unable to get JWT Token")
			} catch (e: ExpiredJwtException) {
				println("JWT Token has expired")
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String")
		}

		//Once we get the token validate it.
		if (SecurityContextHolder.getContext().authentication == null) {

			val userDetails: UserDetails = this.jwtUserDetailsService.loadUserByUsername(username)

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				val usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.authorities)
				usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
			}
		}
		chain.doFilter(request, response)
	}

}
