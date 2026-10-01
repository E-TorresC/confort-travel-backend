package com.conforttravel.api.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Agrupamos los endpoints operativos en un arreglo para mantener la configuración limpia (Clean Code)
        String[] endpointsOperativos = {
                "/api/clientes/**",
                "/api/ventas/**",
                "/api/venta-servicios/**",
                "/api/viajes/**",
                "/api/tramos/**",
                "/api/reservas/**",
                "/api/checkins/**",
                "/api/notificaciones/**",
                "/api/aerolineas/**",
                "/api/servicios/**"
        };

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // 2. Rutas Públicas (Autenticación, Swagger, Actuator)
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/actuator/health",
                                "/actuator/health/**",
                                "/actuator/info"
                        ).permitAll()

                        // 3. Mantenimiento del Sistema (Full access y GET exclusivos para ADMIN)
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/auditoria/**").hasRole("ADMIN")

                        // 4. Endpoints Operativos - Visualización (GET permitidos para ADMIN y AGENTE)
                        .requestMatchers(HttpMethod.GET, endpointsOperativos).hasAnyRole("ADMIN", "AGENTE")

                        // 5. Endpoints Operativos - Mutación (Restringidos SOLO para AGENTE)
                        .requestMatchers(HttpMethod.POST, endpointsOperativos).hasRole("AGENTE")
                        .requestMatchers(HttpMethod.PUT, endpointsOperativos).hasRole("AGENTE")
                        .requestMatchers(HttpMethod.PATCH, endpointsOperativos).hasRole("AGENTE")
                        .requestMatchers(HttpMethod.DELETE, endpointsOperativos).hasRole("AGENTE")

                        // 6. Resto de endpoints (Ej. /api/dashboard, /api/reportes) accesibles por usuarios autenticados
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}