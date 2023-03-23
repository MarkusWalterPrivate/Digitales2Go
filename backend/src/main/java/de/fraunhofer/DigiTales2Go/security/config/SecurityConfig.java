package de.fraunhofer.DigiTales2Go.security.config;

import de.fraunhofer.DigiTales2Go.security.jwt.JwtAuthenticationEntryPoint;
import de.fraunhofer.DigiTales2Go.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    private static final String[] SWAGGER_WHITELIST = {
            // -- swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String moderator = "MODERATOR";
        String user = "USER";
        String creator = "CREATOR";
        String admin = "ADMIN";

        http.csrf().disable();
        http.cors();
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/auth/**", "/feed/**", "/shared/**").permitAll();
        http.authorizeRequests().antMatchers(SWAGGER_WHITELIST).permitAll();

        http.authorizeRequests().antMatchers( "/user/**")
                .hasAnyRole(user, moderator, creator, admin);
        http.authorizeRequests().antMatchers(POST, "/swipe/**")
                .hasAnyRole(user, moderator, creator, admin);

        http.authorizeRequests()
                .antMatchers(GET, "/company/**", "/trend/**", "/tech/**", "/project/**",
                        "/user/**")
                .hasAnyRole(user, moderator, creator, admin);

        http.authorizeRequests().antMatchers(DELETE, "/user/**", "/swipe/**")
                .hasAnyRole(user, moderator, creator, admin);

        http.authorizeRequests().antMatchers("/comment/**", "/bookmark/**").hasAnyRole( user, creator, moderator, admin);


        http.authorizeRequests()
                .antMatchers(DELETE, "/company/**", "/trend/**", "/tech/**", "/project/**")
                .hasAnyRole(moderator, admin);

        http.authorizeRequests().antMatchers("/approve/**", "/feed/unapproved/").hasAnyRole(moderator, admin);

        http.authorizeRequests().antMatchers(PUT, "/company/**", "/trend/**", "/tech/**", "/project/**")
                .hasAnyRole(creator, admin);

        http.authorizeRequests().antMatchers(POST, "/company/**", "/trend/**", "/tech/**", "/project/**", "/fileUpload/", "/fileUpload/**")
                .hasAnyRole(creator, admin);


        http.authorizeRequests().anyRequest().hasRole(admin);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}