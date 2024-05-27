package cl.tingeso.reparacion_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sun.istack.NotNull;

@Entity
@Table(name = "reparaciones")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReparacionEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo_reparacion;    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11.
    private String patente;            // 4 letras + 2 numeros.
    private String fecha;
    private String hora;
    private int monto;
}
