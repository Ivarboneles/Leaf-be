package com.example.leaf.config;

import com.example.leaf.exceptions.ResourceNotFoundException;
import com.example.leaf.utils.MapHelper;
import com.example.leaf.dto.response.ResponseObject;
import com.example.leaf.filter.JwtTokenFilter;
import com.example.leaf.repositories.UserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.webjars.NotFoundException;

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
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/auth/login", "/docs/**", "/swagger-ui/index.html#/**", "/verify", "/api/v1/image/**")
                .permitAll()
                .anyRequest()
                .permitAll()
                .and().logout().logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

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
}

