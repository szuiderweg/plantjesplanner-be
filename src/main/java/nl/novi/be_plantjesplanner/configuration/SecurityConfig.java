package nl.novi.be_plantjesplanner.configuration;

import nl.novi.be_plantjesplanner.security.JwtRequestFilter;
import nl.novi.be_plantjesplanner.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SecurityConfig {

  private final CustomUserDetailsService userDetailsService;

  public SecurityConfig( CustomUserDetailsService userDetailsService){
      this.userDetailsService = userDetailsService;
  }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
      return new ProviderManager(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/users/register").permitAll()//create new designer is public
                        .requestMatchers("/users/me").authenticated()//anyone with valid JWT
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/plants/**").hasAnyRole("ADMIN","DESIGNER")
                        .requestMatchers("/plants/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/designs/me/**").hasRole("DESIGNER")
                        .requestMatchers(HttpMethod.POST,   "/designs/me/**").hasRole("DESIGNER")
                        .requestMatchers(HttpMethod.PATCH,  "/designs/me/**").hasRole("DESIGNER")
                        .requestMatchers(HttpMethod.DELETE, "/designs/me/**").hasRole("DESIGNER")
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
