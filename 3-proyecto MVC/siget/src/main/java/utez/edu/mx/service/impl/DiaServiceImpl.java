package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
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

    @Override
    public Dia obtenerPorNombre(String nombreDia) {
        Dia dia = diaRepository.findByNombre(nombreDia);
        if(dia == null){
            throw new SigetException("Día "+nombreDia+", no encontrado.");
        }
        return dia;
    }
}
