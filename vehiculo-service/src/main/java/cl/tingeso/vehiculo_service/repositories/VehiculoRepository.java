package cl.tingeso.vehiculo_service.repositories;


import cl.tingeso.vehiculo_service.entities.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, String> {
    //encontrar vehiculo por patente
    @Query("SELECT v FROM VehiculoEntity v WHERE v.patente = :patente")
    VehiculoEntity findVehiculo(@Param("patente") String patente);
}
