package umb.v1.informationandproductmanagement.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider,
                             JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth",
                                "/auth/refresh-token",
                                "/customer/save",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**")
                        .permitAll()

                        .requestMatchers("/product").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-id").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-brand").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-category").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-platform").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-name").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-price-range").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-brand-and-category").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-platform-and-category").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-brand-category-and-platform").hasRole(CUSTOMER)
                        .requestMatchers("/product/find-by-ram-memory").hasRole(CUSTOMER)
                        .requestMatchers("/product/web-scraper-bot").hasRole(ADMIN)
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("auth/logout")
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext()))
                .build();
    }

}