package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.bean.DivisionAcademicaBean;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.repository.DivisionAcademicaRepository;
import utez.edu.mx.service.DivisionAcademicaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DivisionAcademicaServiceImpl implements DivisionAcademicaService {

    @Autowired
    private DivisionAcademicaRepository divisionAcademicaRepository;
    @Override
    public List<DivisionAcademicaBean> listarDivisionesAcademicas() {
        return divisionAcademicaRepository.findAll().stream().map(divisionAcademica -> Utileria.mapper.map(divisionAcademica, DivisionAcademicaBean.class)).collect(Collectors.toList());
    }
}
