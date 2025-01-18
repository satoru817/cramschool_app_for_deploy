package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/schools-by-username","/signUp**", "/userRegister", "/css/**", "/js/**", "/", "/passwordChange", "/storage/**","/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN","SUPER_ADMIN") // すべてのPOSTリクエストにADMINあるいはSUPER_ADMIN権限を必要とする。GENERALアカウントはできない。GENERALアカウントはバイト用
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/register").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/success")
                        .permitAll()
                )
                .csrf(csrf->csrf
                        .ignoringRequestMatchers("/api/schools-by-username"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
