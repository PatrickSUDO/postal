package com.wcc.postal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;



@Configuration // This annotation tells Spring that this is a configuration class.
@EnableWebSecurity // This annotation switches on the web security in Spring.
public class SecurityConfiguration {
    @Bean // This annotation tells Spring to use this method to create an instance of UserDetailsService.
    public UserDetailsService userDetailsService() {
        // Create an encoder that Spring Security uses for password hashing.
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Create an in-memory user detail service.
        var userDetailsService = new InMemoryUserDetailsManager();
        // Create a user (in a real system you would probably fetch the user data from a database).
        var user = User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("USER").build(); // Assign user role. In a real system, roles can be used to manage permissions.
        userDetailsService.createUser(user); // Add the user to the service.
        return userDetailsService; // Return the service.
    }

    @Bean // This annotation tells Spring to use this method to create an instance of SecurityFilterChain.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Only users with the "USER" role can access "/updatePostcode"
                        .requestMatchers("/updatePostcode").hasRole("USER")
                        // All other requests are permitted without authentication.
                        .anyRequest().authenticated())
                // Disabling httpBasic and formLogin as these are not needed for REST APIs.
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build(); // Build and return the configuration.
    }
}