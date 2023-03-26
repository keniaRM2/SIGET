package utez.edu.mx.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.ViewConstants;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(PathConstants.INDEX).setViewName(ViewConstants.INDEX);
        registry.addViewController(PathConstants.INDEX_NAME).setViewName(ViewConstants.INDEX);
        registry.addViewController(PathConstants.LOGIN).setViewName(ViewConstants.LOGIN);
        registry.addViewController(PathConstants.ERROR).setViewName(ViewConstants.INDEX);

    }
}
