package cl.tingeso.vehiculo_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sun.istack.NotNull;

@Entity
@Table(name = "vehiculos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehiculoEntity {
    @Id
    @NotNull
    private String patente;     // 4 letras + 2 numeros.
    private String marca;       // toyota, volkswagen, etc.
    private String modelo;      // corolla, golf, etc.
    private String tipo;        // sedan, hatchback, suv, pickup, furgoneta.
    private int fabricacion;    // año fabricacion
    private String motor;       // gasolina, diesel, hibrido, electrico.
    private int asientos;       // numero de asientos
}
