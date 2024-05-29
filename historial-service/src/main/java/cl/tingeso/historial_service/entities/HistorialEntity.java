package cl.tingeso.historial_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sun.istack.NotNull;

@Entity
@Table(name = "historial")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistorialEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha_ingreso;   // ingreso reparacion
    private String hora_ingreso;
    private int monto_reparaciones;
    private int monto_recargos;
    private int monto_descuentos;
    private int monto_iva;
    private int costo_total;
    private String fecha_salida;    // salida reparacion
    private String hora_salida;
    private String fecha_retiro;    // cuando el cliente retira el vehiculo
    private String hora_retiro;
    private String patente;         // 4 letras + 2 numeros.
}
