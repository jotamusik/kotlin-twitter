package com.jotamusik.kotlintwitter;

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.ArrayList

@Component
class JwtUserDetailsService : UserDetailsService {

	override fun loadUserByUsername(username: String): UserDetails {
		if ("someone" == username) {
			return User("someone", "$2a$10\$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", ArrayList());
		} else {
			throw UsernameNotFoundException("User not found with username: $username");
		}
	}
}
