package ar.edu.davinci.excusas.repository;

import ar.edu.davinci.excusas.model.EstadoExcusa;
import ar.edu.davinci.excusas.model.Excusa;
import ar.edu.davinci.excusas.model.Motivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExcusaRepository extends JpaRepository<Excusa, Long> {

    @Query("select e from Excusa e where e.empleado.numeroLegajo = :legajo order by e.fecha")
    List<Excusa> buscarPorLegajo(@Param("legajo") int legajo);

    //los filtros que no llegan quedan en null y no se aplican
    @Query("select e from Excusa e where e.empleado.numeroLegajo = :legajo"
            + " and (:desde is null or e.fecha >= :desde) and (:hasta is null or e.fecha <= :hasta) order by e.fecha")
    List<Excusa> buscarPorLegajoEntreFechas(@Param("legajo") int legajo,
                                            @Param("desde") LocalDate desde,
                                            @Param("hasta") LocalDate hasta);

    @Query("select e from Excusa e where (:desde is null or e.fecha >= :desde) and (:hasta is null or e.fecha <= :hasta)"
            + " and (:motivo is null or e.motivo = :motivo) and (:encargado is null or e.aceptadaPor = :encargado)"
            + " order by e.fecha")
    List<Excusa> filtrar(@Param("desde") LocalDate desde,
                         @Param("hasta") LocalDate hasta,
                         @Param("motivo") Motivo motivo,
                         @Param("encargado") String encargado);

    List<Excusa> findByEstado(EstadoExcusa estado);

    @Modifying
    @Query("delete from Excusa e where e.fecha < :fechaLimite")
    int eliminarAnterioresA(@Param("fechaLimite") LocalDate fechaLimite);
}
