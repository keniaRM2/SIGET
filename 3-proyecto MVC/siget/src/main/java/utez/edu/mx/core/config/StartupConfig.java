package utez.edu.mx.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import utez.edu.mx.service.impl.InicialServiceImpl;

@Component
public class StartupConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private InicialServiceImpl inicialService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("onApplication-> "+ event.getApplicationContext().getApplicationName());
        inicialService.cargarInformacionInicial();
    }
}
// nota no descomentar por el momento, carga de datos iniciales en la base de datos.