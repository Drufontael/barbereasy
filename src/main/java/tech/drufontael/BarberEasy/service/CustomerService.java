package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.repository.CustomerRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.util.List;
@Service
public class CustomerService {

    @Autowired
    private  CustomerRepository repository;

    public void save(Customer obj){
        repository.save(obj);
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer findByUsername(String username){
        return repository.findByUsername(username).orElseThrow(()->
                new UserException("Customer not found"));
    }

}
