package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.BitacoraAcceso;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.BitacoraAccesoRepository;
import utez.edu.mx.dao.repository.UsuarioRepository;
import utez.edu.mx.service.BitacoraAccesoService;

import java.util.List;

@Service
public class BitacoraAccesoServiceImpl implements BitacoraAccesoService {

    @Autowired
    private BitacoraAccesoRepository bitacoraAccesoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void registrarIngreso(String username)  {
        try{
            Usuario usuario  = usuarioRepository.findByUsername(username);
            BitacoraAcceso bitacoraAcceso = new BitacoraAcceso();
            bitacoraAcceso.setUsuario(usuario);
            bitacoraAcceso.setFechaInicio(Utileria.getFechaHoraActual());
            bitacoraAccesoRepository.save(bitacoraAcceso);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void registrarSalida(String username)  {
        try{
            BitacoraAcceso bitacoraAcceso = null;
            Usuario usuario  = usuarioRepository.findByUsername(username);
            List<BitacoraAcceso> bitacoraAccesos = bitacoraAccesoRepository.findAllByUsuarioAndFechaFinIsNullOrderByFechaInicioDesc(usuario);

            if(Utileria.nonEmptyList(bitacoraAccesos)){
                bitacoraAcceso = bitacoraAccesos.get(0);
            }else{
                bitacoraAcceso = new BitacoraAcceso();
                bitacoraAcceso.setFechaInicio(Utileria.getFechaHoraActual());
                bitacoraAcceso.setUsuario(usuario);
            }
            bitacoraAcceso.setFechaFin(Utileria.getFechaHoraActual());
            bitacoraAccesoRepository.save(bitacoraAcceso);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


}
