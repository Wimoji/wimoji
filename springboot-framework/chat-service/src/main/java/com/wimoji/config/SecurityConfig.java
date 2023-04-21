package com.wimoji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			// 권한이 필요한 경로는 여기에 추가합니다.
			.anyRequest().permitAll();
			// .and()
			// .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
	}
}