package cl.tingeso.reparacion_service.services;

import cl.tingeso.reparacion_service.entities.ReparacionEntity;
import cl.tingeso.reparacion_service.repositories.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;

    public void guardarReparacion(String tipo_reparacion, String patente, String fecha, String hora, int monto){
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setTipo_reparacion(tipo_reparacion);
        reparacion.setPatente(patente);
        reparacion.setFecha(fecha);
        reparacion.setHora(hora);
        reparacion.setMonto(monto);
        reparacionRepository.save(reparacion);
    }

    public void eliminarReparaciones(){
        reparacionRepository.deleteAll();
    }

    public List<ReparacionEntity> obtenerReparaciones(){
        return reparacionRepository.findAll();
    }

    public List<ReparacionEntity> obtenerReparacionesPorTipo(String tipo_reparacion){
        return reparacionRepository.findReparacionesByTipo(tipo_reparacion);
    }

    public List<ReparacionEntity> obtenerReparacionesPorPatente(String patente){
        return reparacionRepository.findReparacionesByPantente(patente);
    }
}
