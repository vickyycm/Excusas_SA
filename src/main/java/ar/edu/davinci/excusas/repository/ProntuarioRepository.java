package ar.edu.davinci.excusas.repository;

import ar.edu.davinci.excusas.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    //antes de borrar excusas viejas hay que borrar sus prontuarios
    @Modifying
    @Query("delete from Prontuario p where p.excusa.id in (select e.id from Excusa e where e.fecha < :fechaLimite)")
    int eliminarDeExcusasAnterioresA(@Param("fechaLimite") LocalDate fechaLimite);
}
