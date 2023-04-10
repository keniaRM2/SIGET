package utez.edu.mx.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.Carrera;
import utez.edu.mx.dao.repository.CarreraRepository;
import utez.edu.mx.service.CarreraService;

import java.util.List;

@Service
public class CarreraServiceImpl implements CarreraService {


    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public List<Carrera> listarCarreras() {
        return carreraRepository.findAll();
    }
}
