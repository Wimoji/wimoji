package com.wimoji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// security 관련 설정
		http
			.httpBasic().disable()
			.csrf().disable()
			.cors()// cors 허용
			.and()
			.authorizeRequests()
			.anyRequest().permitAll();

		return http.build();
	}
}