package cl.tingeso.reparacion_service.controllers;

import cl.tingeso.reparacion_service.entities.ReparacionEntity;
import cl.tingeso.reparacion_service.services.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparaciones")
public class ReparacionController {
    @Autowired
    private ReparacionService reparacionService;

    @GetMapping("/all")
    public ResponseEntity<List<ReparacionEntity>> getAllReparaciones(){
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparaciones();
        if(reparaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reparaciones);
    }

    @GetMapping("/{tipo_reparacion}")
    public ResponseEntity<List<ReparacionEntity>> getReparacionesByTipo(@RequestParam("tipo_reparacion") String tipo_reparacion){
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacionesPorTipo(tipo_reparacion);
        if(reparaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reparaciones);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<List<ReparacionEntity>> getReparacionesByPatente(@RequestParam("patente") String patente){
        List<ReparacionEntity> reparaciones = reparacionService.obtenerReparacionesPorPatente(patente);
        if(reparaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reparaciones);
    }

    @PostMapping("/new")
    public String createReparacion(@RequestParam("tipo_reparacion") String tipo_reparacion,
                                   @RequestParam("patente") String patente,
                                   @RequestParam("fecha") String fecha,
                                   @RequestParam("hora") String hora,
                                   @RequestParam("monto") int monto){

        reparacionService.guardarReparacion(tipo_reparacion, patente, fecha, hora, monto);
        return "redirect:/reparaciones/new";
    }

    @GetMapping("/delete")
    public void deleteReparaciones(){
        reparacionService.eliminarReparaciones();
    }
}
