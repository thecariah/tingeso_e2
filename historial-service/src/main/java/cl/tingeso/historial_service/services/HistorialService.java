package cl.tingeso.historial_service.services;

import cl.tingeso.historial_service.entities.HistorialEntity;
import cl.tingeso.historial_service.repositories.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HistorialService {
    @Autowired
    HistorialRepository historialRepository;

    @Autowired
    RestTemplate restTemplate;

    public void guardarEnHistorial(String fecha_ingreso, String hora_ingreso, int monto_reparaciones,
                                   int monto_recargos, int monto_descuentos, int monto_iva, int costo_total,
                                   String fecha_salida, String hora_salida, String fecha_retiro,
                                   String hora_retiro, String patente){

        HistorialEntity historial_rep = new HistorialEntity();

        historial_rep.setFecha_ingreso(fecha_ingreso);
        historial_rep.setHora_ingreso(hora_ingreso);
        historial_rep.setMonto_reparaciones(monto_reparaciones);
        historial_rep.setMonto_recargos(monto_recargos);
        historial_rep.setMonto_descuentos(monto_descuentos);
        historial_rep.setMonto_iva(monto_iva);
        historial_rep.setCosto_total(costo_total);
        historial_rep.setFecha_salida(fecha_salida);
        historial_rep.setHora_salida(hora_salida);
        historial_rep.setFecha_retiro(fecha_retiro);
        historial_rep.setHora_retiro(hora_retiro);
        historial_rep.setPatente(patente);
        historialRepository.save(historial_rep);
    }

    public void deleteHistorial(){ historialRepository.deleteAll(); }

    public List<HistorialEntity> obtenerHistorial(){
        return historialRepository.findAll();
    }

    public List<HistorialEntity> historialByVehiculo(String patente){
        return this.historialRepository.historialByPatente(patente);
    }
}
