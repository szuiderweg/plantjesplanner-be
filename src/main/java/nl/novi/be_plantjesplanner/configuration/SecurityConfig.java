package nl.novi.be_plantjesplanner.configuration;

import nl.novi.be_plantjesplanner.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    private final DataSource dataSource;
//
//    private final JwtRequestFilter jwtRequestFilter;
//    public SecurityConfig(DataSource dataSource, JwtRequestFilter jwtRequestFilter) {
//        this.dataSource = dataSource;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?");
        manager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");
        return manager;
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        //noinspection removal
        http.csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/users/register","/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
                .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled" +
                        " FROM users" +
                        " WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority" +
                        " FROM authorities " +
                        " WHERE username=?");
        return authenticationManagerBuilder.build();
    }
}
