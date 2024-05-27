package cl.tingeso.reparacion_service.repositories;

import cl.tingeso.reparacion_service.entities.ReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity, Long> {
    // encontrar reparaciones segun el tipo de reparacion
    @Query("SELECT r FROM ReparacionEntity r WHERE r.tipo_reparacion = :tipo_reparacion")
    List<ReparacionEntity> findReparacionesByTipo(@Param("tipo_reparacion") String tipo_reparacion);

    // encontrar reparaciones segun la patente del vehiculo
    @Query("SELECT r FROM ReparacionEntity r WHERE r.patente = :patente")
    List<ReparacionEntity> findReparacionesByPantente(@Param("patente") String patente);
}
