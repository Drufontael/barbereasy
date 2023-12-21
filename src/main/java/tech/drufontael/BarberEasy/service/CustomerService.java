package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.event.DeleteEntityEvent;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.repository.CustomerRepository;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private  CustomerRepository repository;

    private final ApplicationEventPublisher eventPublisher;

    public CustomerService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Customer save(Customer obj){
        return repository.save(obj);
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer findById(UUID idCustomer) {
        return repository.findById(idCustomer).orElseThrow(()->new UserException("Customer not found with id:"+idCustomer) );
    }

    public Object update(UUID id, Customer source) {
        var target=findById(id);
        Util.copyNonNullProperties(source,target);
        return repository.save(target);
    }

    @Transactional
    public void delete(UUID id) {
        try {
            eventPublisher.publishEvent(new DeleteEntityEvent(id));
        }catch (ReservationException e){
            throw new ReservationException(e.getMessage());
        }
        var obj=findById(id);
        repository.delete(obj);
    }
}
