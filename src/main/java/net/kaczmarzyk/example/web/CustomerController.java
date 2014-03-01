package net.kaczmarzyk.example.web;

import net.kaczmarzyk.example.domain.Customer;
import net.kaczmarzyk.example.repo.CustomerRepository;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepo;

    @RequestMapping("")
    @ResponseBody
    public Iterable<Customer> listAllCustomers() {
        return customerRepo.findAll();
    }

    @RequestMapping(value = "", params = { "lastName" })
    @ResponseBody
    public Iterable<Customer> filterCustomersByLastName(
            @Spec(params = "lastName", spec = Like.class) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
}
