package com.sas.social.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
    			.csrf(csrf -> csrf.disable())  
    			.authorizeHttpRequests( auth -> auth
    					.requestMatchers("/signup").permitAll()
    					.requestMatchers("/login").permitAll()
    					.anyRequest().authenticated()
    			)
    			.httpBasic(Customizer.withDefaults())
    			.build();
    }
    
//    @Bean
//    public AuthenticationProvider authenticationManager() {
//    		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    		provider.setPasswordEncoder(new BCryptPasswordEncoder(12) );
//    		provider.setUserDetailsService(userDetailsService);
//    		return provider;
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
