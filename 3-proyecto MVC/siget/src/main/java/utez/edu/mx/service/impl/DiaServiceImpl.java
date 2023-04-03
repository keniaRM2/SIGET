package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.Dia;
import utez.edu.mx.dao.repository.DiaRepository;
import utez.edu.mx.service.DiaService;

import java.util.List;

@Service
public class DiaServiceImpl implements DiaService {

    @Autowired
    private DiaRepository diaRepository;

    @Override
    public List<Dia> listarDias() {
        return diaRepository.findAll(Sort.by("id").ascending());
    }
}
