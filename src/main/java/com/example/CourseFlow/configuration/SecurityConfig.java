package com.example.CourseFlow.configuration;

import com.example.CourseFlow.entity.Student;
import com.example.CourseFlow.repository.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class SecurityConfig {

    @Bean
    public UserDetailsService studentUserDetailsService(StudentRepository studentRepository) {
        return email -> {
            Student student = studentRepository.findByEmail(email);
            if (student != null) {
                return User.builder()
                        .username(student.getEmail())
                        .password(student.getPassword())
                        .roles("STUDENT")
                        .build();
            } else {
                throw new UsernameNotFoundException("Student not found");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider studentAuthProvider(UserDetailsService studentUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider studentAuthProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(studentAuthProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/register", "/login", "/error").permitAll()
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/student/courses", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}