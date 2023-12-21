package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.repository.CustomerRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private  CustomerRepository repository;

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
        var obj=findById(id);
        repository.delete(obj);
    }
}
