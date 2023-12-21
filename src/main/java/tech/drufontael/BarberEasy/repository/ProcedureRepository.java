package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Procedure;

import java.util.UUID;

public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

}
