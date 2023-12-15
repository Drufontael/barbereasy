package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.model.Procedure;

import java.util.List;
import java.util.Optional;

public interface ProcedureRepository extends JpaRepository<Procedure,Long> {

    List<Procedure> findAll();

}
