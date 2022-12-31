package com.example.leaf.config;

import com.example.leaf.entities.User;
import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.utils.JWTAuthorizationFilter;
import com.example.leaf.utils.MapHelper;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.utils.JwtTokenFilter;
import com.example.leaf.repositories.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private UserRepository userRepository;

    @Bean
    UserDetailsService userDetailsService() {
        //Find user by user name or phone or email
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<User> userOp = userRepository.findUserByUsername(username);
                if(userOp.isPresent()){
                    return userOp.get();
                }
                if (username.contains("@"))
                    return userRepository.findUserByEmail(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User " + username + " not found"));
                else
                    return userRepository.findUserByPhone(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User " + username + " not found"));

            }
        };
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //Config Spring Security
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //Authorize api
        http
                .authorizeRequests()
                .antMatchers("/api/v1/login/**",
                         "/api/v1/forgot-password", "/api/v1/user/create").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").hasRole("CUSTOMER")
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();


        // Handle exception config
        http.exceptionHandling()
                .accessDeniedHandler(
                        ((request, response, accessDeniedException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_OK);
                            Map<String, Object> map = new HashMap<>();
                            ResponseObject responseObject = new ResponseObject(HttpStatus.UNAUTHORIZED,
                                    accessDeniedException.getMessage());
                            map = MapHelper.convertObject(responseObject);
                            response.getWriter().write(new JSONObject(map).toString());
                        }));

        http.exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_OK);
                            Map<String, Object> map = new HashMap<>();
                            ResponseObject responseObject = new ResponseObject(HttpStatus.UNAUTHORIZED, authException.getMessage());
                            map = MapHelper.convertObject(responseObject);
                            response.getWriter().write(new JSONObject(map).toString());
                        }));

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    protected CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

