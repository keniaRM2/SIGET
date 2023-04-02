package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.dao.model.Horario;
import utez.edu.mx.dao.model.Persona;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.DiaRepository;
import utez.edu.mx.dao.repository.EmpleadoRepository;
import utez.edu.mx.service.EmpleadoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll(Sort.by("id").ascending());
    }

}
