package utez.edu.mx.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(PathConstants.INDEX).setViewName(VistasConstants.INDEX);
        registry.addViewController(PathConstants.INDEX_NAME).setViewName(VistasConstants.INDEX);
        registry.addViewController(PathConstants.LOGIN).setViewName(VistasConstants.LOGIN);
        registry.addViewController(PathConstants.ERROR).setViewName(VistasConstants.INDEX);
        registry.addViewController(PathConstants.CALENDARIO_CITA_ADMIN).setViewName(VistasConstants.CALENDARIO_CITA_ADMIN);
        registry.addViewController(PathConstants.CALENDARIO_CITA_EMPLEADO).setViewName(VistasConstants.CALENDARIO_CITA_EMPLEADO);
    }
}
