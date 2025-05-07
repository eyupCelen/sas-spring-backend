package com.sas.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	@Autowired
//	UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/signup", "/login").permitAll()
	                    .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                    .loginPage("/login")            // custom login page
	                    .defaultSuccessUrl("/home", true) // redirect after successful login
	                    .permitAll()
	            )
	            .logout(logout -> logout
	                    .logoutUrl("/logout")            // logout URL
	                    .logoutSuccessUrl("/login")      // redirect after logout
	                    .permitAll()
	            )
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
