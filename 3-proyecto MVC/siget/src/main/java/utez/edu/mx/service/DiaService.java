package utez.edu.mx.service;

import utez.edu.mx.dao.model.Dia;

import java.util.List;

public interface DiaService {

    List<Dia> listarDias();

    Dia obtenerPorNombre(String nombreDia);
}
