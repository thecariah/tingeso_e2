package cl.tingeso.historial_service.repositories;

import cl.tingeso.historial_service.entities.HistorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<HistorialEntity, Long> {
    // mostrar el historial de reparaciones de un vehiculo (segun patente)
    @Query("SELECT h FROM HistorialEntity h WHERE h.patente = :patente")
    List<HistorialEntity> historialByPatente(@Param("patente") String patente);
}
