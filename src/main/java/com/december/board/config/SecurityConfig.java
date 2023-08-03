package com.december.board.config;

import com.december.board.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService uds;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/post").hasAuthority("USER")
                .requestMatchers("/comment").hasAuthority("USER")
                .requestMatchers("/comment/**").hasAuthority("USER")
                .anyRequest().permitAll());

        http.formLogin(Customizer.withDefaults());

        http.logout(
                (logout)->logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
        );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(uds);
        return daoAuthenticationProvider;
    }
}
