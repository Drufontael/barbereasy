package tech.drufontael.BarberEasy.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.drufontael.BarberEasy.model.*;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.*;
import tech.drufontael.BarberEasy.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BarberService barberService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private ReservationService reservationService;


    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void run(String... args) throws Exception {
        // Criar e salvar 5 barbeiros
        for (int i = 1; i <= 5; i++) {
            Barber barber = new Barber(null, "Barbeiro Criativo " + i, "barbeiro" + i + "@hairstudio.com",
                    "senha" + i, "Expert em cortes modernos e estilos únicos.");
            barberService.save(barber);
        }

        // Criar e salvar 10 clientes
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(null, "Cliente Estiloso " + i, "cliente" + i + "@mail.com",
                    "senha" + i,"Carcteristica"+i);
            customerService.save(customer);
        }

        // Criar e salvar 1 gerente
        Manager manager = new Manager(null, "Gerente Fashion", "gerente@hairstudio.com", "senha");
        managerService.save(manager);

        // Criar e salvar 5 procedimentos
        for(int i=1; i<=5; i++){
            Procedure procedure=new Procedure(null,"Corte estilo "+i,i%2==0?"Lindo"+i:"Barbaro"+i,i*10);
            procedureService.save(procedure);
        }

        List<Customer> clientes = customerService.findAll();
        List<Barber> barbeiros = barberService.findAll();
        List<Procedure> procedimentos = procedureService.findAll();





        barbeiros.get(1).getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(3),procedimentos.get(4)));
        barbeiros.get(0).getProcedures().addAll(Arrays.asList(procedimentos.get(0),procedimentos.get(3),procedimentos.get(4)));
        barbeiros.get(2).getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(2),procedimentos.get(4)));
        barbeiros.get(3).getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(3),procedimentos.get(2)));
        barbeiros.get(4).getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(3),procedimentos.get(0)));

        barberService.saveAll(barbeiros);


        Reservation r1=new Reservation(null,clientes.get(1),barbeiros.get(3),
                LocalDateTime.parse("25/12/2023 14:30",formatter),
                ReservationStatus.PENDING);
        Reservation r2=new Reservation(null,clientes.get(3),barbeiros.get(2),
                LocalDateTime.parse("25/12/2023 16:30",formatter),
                ReservationStatus.PENDING);
        Reservation r3=new Reservation(null,clientes.get(8),barbeiros.get(1),
                LocalDateTime.parse("25/12/2023 11:30",formatter),
                ReservationStatus.PENDING);
        Reservation r4=new Reservation(null,clientes.get(6),barbeiros.get(3),
                LocalDateTime.parse("25/12/2023 14:30",formatter),
                ReservationStatus.PENDING);



        r1.getProcedures().addAll(Arrays.asList(procedimentos.get(2),procedimentos.get(4)));
        r2.getProcedures().addAll(Arrays.asList(procedimentos.get(0),procedimentos.get(2)));
        r3.getProcedures().addAll(Arrays.asList(procedimentos.get(1),procedimentos.get(2)));
        r4.getProcedures().addAll(Arrays.asList(procedimentos.get(3),procedimentos.get(2)));

        reservationService.saveAll(Arrays.asList(r1,r2,r3,r4));

    }
}

