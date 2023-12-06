package tech.drufontael.BarberEasy.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.repository.CustomerRepository;
import tech.drufontael.BarberEasy.repository.ManagerRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ManagerRepository managerRepository;

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
    }
}

