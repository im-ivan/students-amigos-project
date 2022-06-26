package com.study.amigoscode.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").hasRole(UserRole.STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "management/**").hasAuthority(UserPermission.COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST, "management/**").hasAuthority(UserPermission.COURSE_WRITE.name())
                .antMatchers(HttpMethod.PUT, "management/**").hasAuthority(UserPermission.COURSE_WRITE.name())
                .antMatchers(HttpMethod.GET, "management/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ivanUser = User.builder()
                .username("ivan")
                .password(passwordEncoder.encode("123123"))
                .roles(UserRole.STUDENT.name())
                .build();

        UserDetails mylenaUser = User.builder()
                .username("mylena")
                .password(passwordEncoder.encode("senha123"))
                .roles(UserRole.ADMIN.name())
                .build();

        UserDetails nelsonUser = User.builder()
                .username("nelson")
                .password(passwordEncoder.encode("neolítico"))
                .roles(UserRole.ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(
                ivanUser,
                mylenaUser
        );
    }
}
