package cl.tingeso.vehiculo_service.controllers;

import cl.tingeso.vehiculo_service.entities.VehiculoEntity;
import cl.tingeso.vehiculo_service.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/all")
    public ResponseEntity<List<VehiculoEntity>> getAllVehiculos(){
        List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculos();
        if(vehiculos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoEntity> getVehiculo(@PathVariable("patente") String patente){
        VehiculoEntity vehiculo = vehiculoService.encontrarVehiculo(patente);

        if(vehiculo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehiculo);
    }

    @PostMapping("/new")
    public String createVehiculo(@RequestParam("patente") String patente,
                                 @RequestParam("marca") String marca,
                                 @RequestParam("modelo") String modelo,
                                 @RequestParam("tipo") String tipo,
                                 @RequestParam("fabricacion") int fabricacion,
                                 @RequestParam("motor") String motor,
                                 @RequestParam("asientos") int asientos){

        vehiculoService.guardarVehiculo(patente, marca, modelo, tipo, fabricacion, motor, asientos);
        return "redirect:/vehiculos/new";
    }

    @GetMapping("/delete")
    public void deleteVehiculos(){
        vehiculoService.deleteVehiculos();
    }

}
