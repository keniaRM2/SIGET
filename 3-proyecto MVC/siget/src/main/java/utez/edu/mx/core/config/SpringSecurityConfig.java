package utez.edu.mx.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfig {

    private static final String[] PATH_TODO_PERMITIDO = {
            PathConstants.ASSETS_ALL_SUBPATHS,
            "/error/**",
            "/api/" + PathConstants.LISTAR_CITAS
    };

    private static final String[] PATH_SIN_SESION = {
            PathConstants.LOGIN_ALL_SUBPATHS
    };
    private static final String[] PATH_ROL_ADMIN = {
            PathConstants.LISTAR_EMPLEADOS + PathConstants.ALL_SUBPATHS,
            PathConstants.GUARDAR_EMPLEADO,
            PathConstants.REGISTRAR_EMPLEADO,
            PathConstants.EDITAR_EMPLEADO + PathConstants.ALL_SUBPATHS,
            PathConstants.ACTUALIZAR_ESTATUS_EMPLEADO + PathConstants.ALL_SUBPATHS,

            PathConstants.LISTAR_DOCUMENTOS_SERVICIO,
            PathConstants.LISTAR_SERVICIOS + PathConstants.ALL_SUBPATHS,
            PathConstants.EDITAR_SERVICIO,
            PathConstants.REGISTRAR_SERVICIO,
            PathConstants.REMOVER_REMOVER_DOCUMENTO,
            PathConstants.ACTUALIZAR_ESTATUS_SERVICIO,
            PathConstants.GUARDAR_SERVICIO,

            PathConstants.LISTAR_ALUMNOS + PathConstants.ALL_SUBPATHS

    };
    private static final String[] PATH_ROL_EMPLEADO = {
            PathConstants.CALENDARIO_CITA_EMPLEADO,
            PathConstants.INFORMACION_CITA + PathConstants.ALL_SUBPATHS,
            PathConstants.EDITAR_ESTADO_CITA
    };

    private static final String[] PATH_ROL_ALUMNO = {
            PathConstants.EDITAR_ESTADO_CITA
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
                                .requestMatchers(PATH_ROL_ADMIN).hasAnyAuthority(GeneralConstants.ROL_ADMIN)
                                .requestMatchers(PATH_ROL_EMPLEADO).hasAnyAuthority(GeneralConstants.ROL_EMPLEADO)
                                .requestMatchers(PATH_ROL_ALUMNO).hasAnyAuthority(GeneralConstants.ROL_ALUMNO)
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
