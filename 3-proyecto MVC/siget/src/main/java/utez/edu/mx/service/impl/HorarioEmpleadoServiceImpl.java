package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.DiaRepository;
import utez.edu.mx.dao.repository.HorarioRepository;
import utez.edu.mx.dao.repository.VentanillaRepository;
import utez.edu.mx.service.HorarioEmpleadoService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioEmpleadoServiceImpl  implements HorarioEmpleadoService {

    @Autowired
    private DiaRepository diaRepository;
    @Autowired
    private VentanillaRepository ventanillaRepository;
    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> listarHorario() {
        return horarioRepository.findAll(Sort.by("id").ascending());
    }


    @Override
    public List<Dia> listarDias() {return diaRepository.findAll(Sort.by("id").ascending());}


    @Override
    public List<Timestamp> listarHoras() {
        List<Timestamp> horasDelDia = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {

            for (int i = 7; i < 21; i++) {
                for (int j = 0; j < 4; j++) {
                    Date date = dateFormat.parse(String.format("%02d:%02d", i, j * 15));
                    Timestamp timestamp = new Timestamp(date.getTime());
                    horasDelDia.add(timestamp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return horasDelDia;
    }



    @Override
    public void guardar(Horario horario) throws SigetException {
        if (Utileria.nonNull(horario.getId())) {
            actualizar(horario);
        } else {
            registrar(horario);
        }
    }

    @Override
    @Transactional
    public void registrar(Horario horario) throws SigetException {
        try {


            Horario horarioRepetido = horarioRepository.findAllByVentanillaAndEmpleadoAndDiaAndHoraInicioBetween(
                    horario.getVentanilla(),
                    horario.getEmpleado(),
                    horario.getDia(),
                    horario.getHoraInicio(),
                    horario.getHoraFin());
            if (Utileria.nonNull(horarioRepetido)) {
                throw new SigetException("El horario se ha registrado previamente.");
            }
            String diaNombre = horario.getDia().getNombre();
            if (Utileria.isEmpty(diaNombre)) {
                throw new SigetException("Debe seleccionar un dia.");
            }
            if (horario.getHoraInicio().equals(horario.getHoraFin()) ||
                    horario.getHoraInicio().compareTo(horario.getHoraFin()) >= 0) {
                throw new SigetException("Horas invalidas");
            }


            Dia dia = horario.getDia();
            Ventanilla ventanilla = horario.getVentanilla();
            horario.setVentanilla(ventanilla);
            horario.setDia(dia);

            horarioRepository.save(horario);
            diaRepository.save(dia);
            ventanillaRepository.save(ventanilla);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public void actualizar(Horario horario) throws SigetException {

    }


//    @Override
//    public void actualizar(Horario horario) throws SigetException {
//
//        try {
//
//            Optional<Horario> horaioOpcional = horarioRepository.findById(horario.getId());
//            if (horaioOpcional.isEmpty()) {
//                throw new SigetException("El horario no fue encontrado.");
//            }
//            Servicio horarioRepetido = horarioRepository.findByNombreAndIdIsNot(servicio.getNombre(), servicio.getId());
//
//        }catch (SigetException e) {
//            throw new SigetException(e.getMessage());
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            throw new SigetException(Utileria.getErrorNull());
//        }
//    }





    @Override
    public void borrarHorario(Integer id) throws SigetException {

    }

    @Override
    public Horario obtenerHorarioRegistro() {
        return null;
    }


}
