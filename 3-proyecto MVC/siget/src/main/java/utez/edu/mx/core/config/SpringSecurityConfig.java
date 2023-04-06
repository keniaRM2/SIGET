package utez.edu.mx.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.service.BitacoraAccesoService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] PATH_TODO_PERMITIDO = {
            PathConstants.ASSETS_ALL_SUBPATHS,
            PathConstants.ERROR+PathConstants.ALL_SUBPATHS
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
            PathConstants.LISTAR_CITAS
    };
    private static final String[] PATH_ROL_EMPLEADO = {
            PathConstants.LISTAR_CITAS,
            PathConstants.CANCELAR_CITA

    };
    private static final String[] PATH_ROL_ALUMNO = {
            PathConstants.CANCELAR_CITA
    };

    @Autowired
    private LogoutHandler logoutHandler;

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
                        .loginPage(PathConstants.LOGIN)
                        .loginProcessingUrl(PathConstants.LOGIN)
                        .defaultSuccessUrl(PathConstants.INDEX)
                        .permitAll()
                )
                .logout((log) -> log
                        .addLogoutHandler(logoutHandler)
                        .permitAll()
                )
                .exceptionHandling((exh) ->
                        exh.accessDeniedPage(VistasConstants.ERROR)
                );
        return http.build();
    }
}
