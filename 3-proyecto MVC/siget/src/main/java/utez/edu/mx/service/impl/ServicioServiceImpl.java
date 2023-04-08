package utez.edu.mx.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.bean.ServicioBean;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.DocumentoRepository;
import utez.edu.mx.dao.repository.ServicioRepository;
import utez.edu.mx.dao.repository.TipoServicioRepository;
import utez.edu.mx.service.ServicioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void guardar(Servicio servicio) throws SigetException {
        if (Utileria.nonNull(servicio.getId())) {
            actualizar(servicio);
        } else {
            registrar(servicio);
        }
    }


    @Override
    @Transactional
    public void registrar(Servicio servicio) throws SigetException {
        try {

            Servicio servicioRepetido = servicioRepository.findByNombre(servicio.getNombre());

            if (Utileria.nonNull(servicioRepetido)) {
                throw new SigetException("El servicio se ha registrado previamente.");
            }
            if (servicio.getCosto() < 0) {
                throw new SigetException("Costo no válido.");
            }

            servicio.setTipoServicio(
                    servicio.getCosto() == 0 ?
                            tipoServicioRepository.findByNombre(GeneralConstants.TIPO_SERVICIO_GRATUITO) :
                            tipoServicioRepository.findByNombre(GeneralConstants.TIPO_SERVICIO_COSTO)
            );

            if (Utileria.nonEmptyList(servicio.getDocumentos())) {
                for (Documento documento : servicio.getDocumentos()) {
                    documento.setServicio(servicio);
                }
            }

            servicioRepository.save(servicio);

            if (Utileria.nonEmptyList(servicio.getDocumentos())) {
                documentoRepository.saveAll(servicio.getDocumentos());
            }

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    //ACTUALIZA
    @Override
    @Transactional
    public void actualizar(Servicio servicio) throws SigetException {
        try {

            Optional<Servicio> servicioOptional = servicioRepository.findById(servicio.getId());

            if (servicioOptional.isEmpty()) {
                throw new SigetException("servicio no encontrado.");
            }
            Servicio servicioRepetido = servicioRepository.findByNombreAndIdIsNot(servicio.getNombre(), servicio.getId());
            if (Utileria.nonNull(servicioRepetido)) {
                throw new SigetException("El servicio se ha registrado previamente");
            }
            if (servicio.getCosto() < 0) {
                throw new SigetException("Costo no válido.");
            }

            servicio.setTipoServicio(
                    servicio.getCosto() == 0 ?
                            tipoServicioRepository.findByNombre(GeneralConstants.TIPO_SERVICIO_GRATUITO) :
                            tipoServicioRepository.findByNombre(GeneralConstants.TIPO_SERVICIO_COSTO)
            );

            servicioRepository.save(servicio);

            if (Utileria.nonEmptyList(servicio.getDocumentos())) {

                List<Integer> idsDocumentos = servicio.getDocumentos().stream().map(Documento::getId).filter(Utileria::nonNull).collect(Collectors.toList());
                idsDocumentos.add(0); // Se agrega un valor por defecto, para que no sea una lista vacia.
                List<Documento> documentosRemovidos = documentoRepository.findAllByServicioAndIdNotIn(servicio, idsDocumentos);
                if (Utileria.nonEmptyList(documentosRemovidos)) {
                    documentoRepository.deleteAll(documentosRemovidos);
                }


                for (Documento documento : servicio.getDocumentos()) {
                    Documento busqueda = documentoRepository.findByNombreIgnoreCaseAndServicio(documento.getNombre().trim(), servicio);
                    if (Utileria.nonNull(busqueda)) {
                        documento.setId(busqueda.getId());
                    }
                    documento.setServicio(servicio);
                    documentoRepository.save(documento);
                }

            } else {
                List<Documento> documentos = documentoRepository.findAllByServicio(servicio);
                if (Utileria.nonEmptyList(documentos)) {
                    documentoRepository.deleteAll(documentos);
                }
            }

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public Servicio obtenerServicioRegistro(Servicio servicio) {
        if (servicio == null) {
            servicio = new Servicio();
        }
        if (Utileria.isEmptyList(servicio.getDocumentos())) {
            servicio.setDocumentos(new ArrayList<>());
        }
        servicio.getDocumentos().add(new Documento());
        return servicio;
    }

    @Override
    public Servicio obtenerServicioEdicion(Integer id) throws SigetException {

        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if (servicioOptional.isEmpty()) {
            throw new SigetException("servicio no encontrado.");
        }
        return servicioOptional.get();
    }

    @Override
    @Transactional
    public void actualizarEstatus(Integer id) throws SigetException {

        try {
            Optional<Servicio> servicioOptional = servicioRepository.findById(id);
            if (servicioOptional.isEmpty()) {
                throw new SigetException("servicio no encontrado.");
            }

            Servicio servicio = servicioOptional.get();
            if (servicio.getEstatus() == 1) {
                servicio.setEstatus(0);
            } else {
                servicio.setEstatus(1);
            }
            servicioRepository.save(servicio);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }

    }

    @Override
    public Servicio removerDocumento(Servicio servicio, int indice) {
        if (Utileria.nonNull(servicio) && Utileria.nonEmptyList(servicio.getDocumentos())) {
            servicio.getDocumentos().remove(indice);
        }
        return servicio;
    }

    @Override
    public ServicioBean obtenerServicioConDocumentos(Integer idServicio) {
        try{
            Servicio servicio = servicioRepository.findById(idServicio).orElseThrow( () -> new SigetException("Servicio no encontrado"));
            return Utileria.mapper.map(servicio, ServicioBean.class);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}
