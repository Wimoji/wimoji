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
		// security 기본 설정
		http
			// basic auth, csrf 보안, session 미사용
			.httpBasic().disable()
			.csrf().disable()
			.cors()
			.and()// 권한 체크
			.authorizeRequests()
			.anyRequest().permitAll();
		return http.build();
	}
}