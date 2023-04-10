package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.HorarioBean;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.Dia;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.dao.model.Horario;
import utez.edu.mx.dao.model.Servicio;
import utez.edu.mx.dao.repository.HorarioRepository;
import utez.edu.mx.service.DiaService;
import utez.edu.mx.service.EmpleadoService;
import utez.edu.mx.service.HorarioService;
import utez.edu.mx.service.ServicioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private DiaService diaService;

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private ServicioService servicioService;

    @Override
    public List<HorarioBean> validarDisponilidad(CitaBean citaBean) throws SigetException{
        try {


            String nombreDia = Utileria.obteneDiaSemana(citaBean.getFechaCita());
            Dia dia = diaService.obtenerPorNombre(nombreDia);

            Integer activo = GeneralConstants.ESTATUS_ACTIVO;
            List<Empleado> empleados = empleadoService.listarEmpleados().stream().filter(empleado ->
                    Objects.equals(empleado.getPersona().getUsuario().getEnabled(), activo)
            ).toList();

            if (Utileria.isEmptyList(empleados)) {
                throw new SigetException("Sin empleados disponibles, para atender.");
            }


            List<Horario> horarios = horarioRepository.findAllByDiaAndEmpleadoInOrderByHoraInicio(dia, empleados);
            if (Utileria.isEmptyList(horarios)) {
                throw new SigetException("Sin empleados disponibles en ese día, para atender.");
            }

            List<HorarioBean> horariosBean = new ArrayList<>();

            for(Horario horario : horarios){
                horario.getEmpleado().setHorarios(null);

                HorarioBean horarioBean = Utileria.mapper.map(horario, HorarioBean.class);
                horariosBean.add(horarioBean);
            }

            return horariosBean;
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }
}
