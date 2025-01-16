package com.daengdaeng_eodiga.project.Global.Security.config;

import java.util.Arrays;
import java.util.List;

import com.daengdaeng_eodiga.project.Global.Redis.Repository.RedisTokenRepository;
import com.daengdaeng_eodiga.project.user.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RedisTokenRepository redisTokenRepository;
    private final UserService userService;
    private final Boolean testMode;
    public SecurityConfig(
    RedisTokenRepository redisTokenRepository,UserService userService,@Value("${frontend.test}") Boolean testMode) {
        this.redisTokenRepository = redisTokenRepository;
        this.userService = userService;
        this.testMode = testMode;

    }
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:5173","https://pet.daengdaeng-where.link","https://daengdaeng-where-git-test-wldusdns-projects.vercel.app","https://fronttest.daengdaeng-where.link"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Set-Cookie","Access-Control-Allow-Origin"));
        configuration.setExposedHeaders(List.of("Set-Cookie","Access-Control-Allow-Origin"));

        return configuration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());
        http.formLogin((form) -> form.disable());
        http.cors(cors -> cors.configurationSource(request -> corsConfiguration()));

        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/v1/loginSuccess","/login", "/favicon.ico","https://pet.daengdaeng-where.link/login","/api/v1/places/**","/login/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/visit/place/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v2/story").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v2/story/detail/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v2/region/**").permitAll()
                .anyRequest().authenticated());


        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}