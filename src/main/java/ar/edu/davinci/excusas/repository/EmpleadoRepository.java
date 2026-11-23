package ar.edu.davinci.excusas.repository;

import ar.edu.davinci.excusas.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
