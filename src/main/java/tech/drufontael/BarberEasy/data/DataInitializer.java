package tech.drufontael.BarberEasy.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.drufontael.BarberEasy.model.*;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criar e salvar 5 barbeiros
        for (int i = 1; i <= 5; i++) {
            Barber barber = new Barber(null, "Barbeiro Criativo " + i, "barbeiro" + i + "@hairstudio.com", "senha" + i, "Expert em cortes modernos e estilos únicos.");
            barberRepository.save(barber);
        }

        // Criar e salvar 10 clientes
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(null, "Cliente Estiloso " + i, "cliente" + i + "@mail.com", "senha" + i,"Carcteristica"+i);
            userRepository.save(customer);
        }

        // Criar e salvar 1 gerente
        Manager manager = new Manager(null, "Gerente Fashion", "gerente@hairstudio.com", "senha");
        managerRepository.save(manager);

        // Criar e salvar 5 procedimentos
        for(int i=1; i<=5; i++){
            Procedure procedure=new Procedure(null,"Corte estilo "+i,i%2==0?"Lindo"+i:"Barbaro"+i,i*10);
            procedureRepository.save(procedure);
        }

        List<Customer> clientes = userRepository.findAll();
        List<Barber> barbeiros = barberRepository.findAll();
        List<Procedure> procedimentos = procedureRepository.findAll();

        Reservation r1=new Reservation(null,clientes.get(1),barbeiros.get(3), LocalDateTime.of(2023,12,20,12,00), ReservationStatus.PENDING);
        Reservation r2=new Reservation(null,clientes.get(3),barbeiros.get(2), LocalDateTime.of(2023,12,15,9,00), ReservationStatus.PENDING);
        Reservation r3=new Reservation(null,clientes.get(8),barbeiros.get(1), LocalDateTime.of(2023,12,10,22,00), ReservationStatus.PENDING);
        Reservation r4=new Reservation(null,clientes.get(6),barbeiros.get(3), LocalDateTime.of(2023,12,25,12,00), ReservationStatus.PENDING);

        r1.getProcedures().addAll(Arrays.asList(procedimentos.get(2),procedimentos.get(4)));
        r2.getProcedures().addAll(Arrays.asList(procedimentos.get(0),procedimentos.get(2)));
        r3.getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(2)));
        r4.getProcedures().addAll(Arrays.asList(procedimentos.get(3),procedimentos.get(2)));

        reservationRepository.saveAll(Arrays.asList(r1,r2,r3,r4));

    }
}

