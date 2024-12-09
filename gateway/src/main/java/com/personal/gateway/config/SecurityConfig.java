package com.personal.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/roles").hasRole("CEO")
                .antMatchers("/api/role/{role}").hasRole("CEO")
                .antMatchers("/api/role/{oldName}/{newName}").hasRole("CEO")
                .antMatchers("/api/role/{role}/users").hasRole("CEO")
                .antMatchers("/api/roles/users-count").hasRole("CEO")
                .antMatchers("/api/users/search").hasRole("CEO")
                .antMatchers("/api/users").hasRole("CEO")
                .antMatchers("/api/user/{username}").hasRole("DEVELOPER")
                .antMatchers("/api/user").hasRole("DEVELOPER")
                .antMatchers("/api/user/{username}/{role}").hasRole("TEAM_LEAD")
                .antMatchers("/api/user/{username}/delete").hasRole("CEO")
                .antMatchers("/api/teams").hasRole("CEO")
                .antMatchers("/api/team/{name}").hasRole("CEO")
                .antMatchers("/api/team").hasRole("CEO")
                .antMatchers("/api/team/{name}/member/{id}/add").hasRole("TEAM_LEAD")
                .antMatchers("/api/team/{name}/member/{id}/remove").hasRole("TEAM_LEAD")
                .antMatchers("/api/team/{teamName}/add-project/{projectName}").hasRole("CEO")
                .antMatchers("/api/team/{teamName}/remove-project/{projectName}").hasRole("CEO")
                .antMatchers("/api/teams/search").hasRole("CEO")
                .antMatchers("/api/projects").hasRole("CEO")
                .antMatchers("/api/project/{name}").hasRole("CEO")
                .antMatchers("/api/project/{name}/teams").hasRole("CEO")
                .antMatchers("/api/user/{name}/vacations").hasRole("DEVELOPER")
                .antMatchers("/api/vacation").hasRole("DEVELOPER")
                .antMatchers("/api/vacation/{id}/sick-list").hasRole("DEVELOPER")
                .antMatchers("/api/vacation/{id}").hasRole("DEVELOPER")
                .antMatchers("/api/vacation/{id}/approve").hasRole("CEO")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().cors().and()
                .csrf().disable();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails ceo = User
                .withUsername("ceo")
                .password(encoder().encode("password"))
                .roles("CEO")
                .build();

        UserDetails teamLead = User
                .withUsername("team_lead")
                .password(encoder().encode("password"))
                .roles("TEAM_LEAD")
                .build();

        UserDetails developer = User
                .withUsername("developer")
                .password(encoder().encode("password"))
                .roles("DEVELOPER")
                .build();

        return new InMemoryUserDetailsManager(ceo, teamLead, developer);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
