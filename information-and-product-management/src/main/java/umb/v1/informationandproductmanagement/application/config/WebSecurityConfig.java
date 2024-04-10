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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                                "/user/save",
                                "/user/forgot-password",
                                "/user/reset-password",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**")
                        .permitAll()

                        .requestMatchers("/product").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-id").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-brand").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-category").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-platform").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-name").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-price-range").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-brand-and-category").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-platform-and-category").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-brand-category-and-platform").hasRole(CLIENTE)
                        .requestMatchers("/product/find-by-ram-memory").hasRole(CLIENTE)
                        .requestMatchers("/product/find-most-viewed").hasRole(CLIENTE)
                        .requestMatchers("/user/search-history").hasRole(CLIENTE)
                        .requestMatchers("/user/viewed-products").hasRole(CLIENTE)
                        .requestMatchers("/user/find-by-email").hasRole(CLIENTE)
                        .requestMatchers("/user/update").hasRole(CLIENTE)
                        .requestMatchers("/user/update-password").hasRole(CLIENTE)
                        .requestMatchers("/pqrs/find-by-email").hasRole(CLIENTE)
                        .requestMatchers("/pqrs/save").hasRole(CLIENTE)
                        .requestMatchers("/product/web-scraper-bot").hasRole(ADMIN)
                        .requestMatchers("/pqrs/find-all").hasRole(ADMIN)
                        .requestMatchers("/pqrs/update").hasRole(ADMIN)
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .exposedHeaders("*")
                        .allowedOriginPatterns("*");
            }
        };
    }

}
