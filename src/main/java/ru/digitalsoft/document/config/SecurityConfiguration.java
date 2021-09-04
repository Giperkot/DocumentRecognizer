package ru.digitalsoft.document.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import ru.digitalsoft.document.config.filter.AuthFilter;
import ru.digitalsoft.document.service.auth.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthService userService;

    @Autowired
    public SecurityConfiguration(AuthService userService) {
        this.userService = userService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/login", "/login/", "/logout", "/logout/",
                "/api/auth/login", "/api/auth/logout", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login");

        http.addFilterBefore(new AuthFilter(userService), SecurityContextPersistenceFilter.class);
    }

}
