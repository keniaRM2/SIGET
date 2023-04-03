package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.Ventanilla;
import utez.edu.mx.dao.repository.VentanillaRepository;
import utez.edu.mx.service.VentanillaService;

import java.util.List;

@Service
public class VentanillaServiceImpl implements VentanillaService {

    @Autowired
    private VentanillaRepository ventanillaRepository;


    @Override
    public List<Ventanilla> listarVentanillas() {
        return ventanillaRepository.findAll(Sort.by("nombre").ascending());
    }
}
