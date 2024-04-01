package umb.v1.informationandproductmanagement.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.repository.CustomerRepository;

@Configuration
public class ApplicationConfig {

    private final CustomerRepository customerRepository;

    public ApplicationConfig(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return mail -> customerRepository.findByCorreoElectronico(mail)
                .orElseThrow(() -> new ApiException("Claims no validos: Usuario no encontrado", 404));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
