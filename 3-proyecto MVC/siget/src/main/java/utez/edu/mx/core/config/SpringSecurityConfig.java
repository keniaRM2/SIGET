package utez.edu.mx.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.constants.PathConstants;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] PATH_TODO_PERMITIDO = {
            PathConstants.ASSETS_ALL_SUBPATHS,
            "/error/**"
    };

    private static final String[] PATH_SIN_SESION = {
            PathConstants.LOGIN_ALL_SUBPATHS
    };
    private static final String[] PATH_ROL_ADMIN = {

    };
    private static final String[] PATH_ROL_EMPLEADO = {

    };
    private static final String[] PATH_ROL_ALUMNO = {

    };


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) ->
                                requests
                                        .requestMatchers(PATH_TODO_PERMITIDO).permitAll()
                                        .requestMatchers(PATH_SIN_SESION).anonymous()
                                        .requestMatchers(PATH_ROL_ADMIN).hasAnyRole(GeneralConstants.ROL_ADMIN)
                                        .requestMatchers(PATH_ROL_EMPLEADO).hasAnyRole(GeneralConstants.ROL_EMPLEADO)
                                        .requestMatchers(PATH_ROL_ALUMNO).hasAnyRole(GeneralConstants.ROL_ALUMNO)
                                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl(PathConstants.INDEX)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling().accessDeniedPage("/access-denied");
        return http.build();
    }
}