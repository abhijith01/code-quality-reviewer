// C:/code-quality-reviewer/src/main/java/com/abhijith/code_quality_reviewer/config/SecurityConfig.java
package com.abhijith.code_quality_reviewer.config;

import com.abhijith.code_quality_reviewer.entity.User; // Make sure this is your entity
import com.abhijith.code_quality_reviewer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepo userRepo;

    // ... your securityFilterChain method is fine ...
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to access the user creation and login endpoints
                        // NOTE: Spring Security will create the /login endpoint for us!
                        .requestMatchers("/api/users/create", "/login").permitAll()
                        // All other requests (like /api/users/me) must be authenticated
                        .anyRequest().authenticated()
                )
                // This configures the default form login provided by Spring Security
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // The URL to submit the username and password to
                        .successHandler((request, response, authentication) -> {
                            // On success, we can send a 200 OK with a success message
                            response.setStatus(200);
                            response.getWriter().write("Login successful for user: " + authentication.getName());
                        })
                        .failureHandler((request, response, exception) -> {
                            // On failure, send a 401 Unauthorized
                            response.setStatus(401);
                            response.getWriter().write("Login failed: " + exception.getMessage());
                        })
                );

        return http.build();
    }


    // FIX IS HERE:
    // 2. Define how to find a user and build a UserDetails object
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 1. Fetch your custom User entity from the database
            User appUser = userRepo.findByFullName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            // 2. Create a list of authorities from your user's role
            //    (Assuming your User entity has a getRole() method that returns an enum or String)
            GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole().name());

            // 3. Create and return a Spring Security User object
            //    This object implements UserDetails
            return new org.springframework.security.core.userdetails.User(
                    appUser.getFullName(), // The username
                    appUser.getPassword(), // The hashed password
                    List.of(authority)     // The user's roles/permissions
            );
        };
    }

    // ... the rest of your SecurityConfig methods are fine ...
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}