package com.bieliaiev.feedback_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bieliaiev.feedback_bot.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        	.userDetailsService(userService)
            .csrf().disable()
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            	.and()
            .authorizeRequests()
            	.requestMatchers("/feedback-bot-webhook").permitAll()
            	.requestMatchers("/", "/login").permitAll()
            	.requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity
            .formLogin()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll();

        httpSecurity
            .logout()
                .permitAll()
                .logoutSuccessUrl("/");

        return httpSecurity.build();
    }
}
