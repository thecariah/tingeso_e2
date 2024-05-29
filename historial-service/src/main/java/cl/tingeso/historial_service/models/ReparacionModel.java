package cl.tingeso.historial_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReparacionModel {
    private String tipo_reparacion;    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11.
    private String patente;            // 4 letras + 2 numeros.
    private String fecha;
    private String hora;
    private int monto;
}
