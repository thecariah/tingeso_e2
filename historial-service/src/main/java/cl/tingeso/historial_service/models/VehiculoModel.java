package cl.tingeso.historial_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehiculoModel {
    private String patente;     // 4 letras + 2 numeros.
    private String marca;       // toyota, volkswagen, etc.
    private String modelo;      // corolla, golf, etc.
    private String tipo;        // sedan, hatchback, suv, pickup, furgoneta.
    private int fabricacion;    // año fabricacion
    private String motor;       // gasolina, diesel, hibrido, electrico.
    private int asientos;       // numero de asientos
}