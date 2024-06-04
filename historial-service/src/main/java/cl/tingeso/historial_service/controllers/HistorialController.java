package cl.tingeso.historial_service.controllers;

import cl.tingeso.historial_service.entities.HistorialEntity;
import cl.tingeso.historial_service.services.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
public class HistorialController {
    @Autowired
    HistorialService historialService;

    @GetMapping("/all")
    public ResponseEntity<List<HistorialEntity>> getHistorial(){
        List<HistorialEntity> historial = historialService.obtenerHistorial();
        if(historial.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<List<HistorialEntity>> getHistorialByVehiculo(@RequestParam("patente") String patente){
        List<HistorialEntity> historial = historialService.historialByVehiculo(patente);
        if(historial.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }

    @PostMapping("/new")
    public String createReparacionEnHistorial(@RequestParam("fecha_ingreso") String fecha_ingreso,
                                              @RequestParam("hora_ingreso") String hora_ingreso,
                                              @RequestParam("fecha_salida") String fecha_salida,
                                              @RequestParam("hora_salida") String hora_salida,
                                              @RequestParam("fecha_retiro") String fecha_retiro,
                                              @RequestParam("hora_retiro") String hora_retiro,
                                              @RequestParam("patente") String patente,
                                              @RequestParam("bono") int bono,
                                              @RequestParam("kilometraje") int kilometraje){

        historialService.guardarEnHistorial(fecha_ingreso, hora_ingreso, fecha_salida, hora_salida, fecha_retiro,
                hora_retiro, patente, bono, kilometraje);

        return "redirect:/reparaciones/new";
    }

    @GetMapping("/delete")
    public void deleteHistorial(){
        historialService.deleteHistorial();
    }
}
