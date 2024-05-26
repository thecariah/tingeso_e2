package cl.tingeso.vehiculo_service.services;

import cl.tingeso.vehiculo_service.entities.VehiculoEntity;
import cl.tingeso.vehiculo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public void guardarVehiculo(String patente, String marca, String modelo,
                                String tipo, int fabricacion, String motor, int asientos){
        VehiculoEntity vehiculo = new VehiculoEntity();

        vehiculo.setPatente(patente);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setTipo(tipo);
        vehiculo.setFabricacion(fabricacion);
        vehiculo.setMotor(motor);
        vehiculo.setAsientos(asientos);
        vehiculoRepository.save(vehiculo);
    }

    public void deleteVehiculos(){ vehiculoRepository.deleteAll(); }

    public List<VehiculoEntity> obtenerVehiculos(){
        return vehiculoRepository.findAll();
    }

    public VehiculoEntity encontrarVehiculo(String patente){
        return this.vehiculoRepository.findVehiculo(patente);

    }
}
