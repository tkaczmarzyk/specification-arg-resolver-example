package net.kaczmarzyk.example.web;

import net.kaczmarzyk.example.domain.Customer;
import net.kaczmarzyk.example.repo.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepo;
    
    
    @RequestMapping("/customers")
    @ResponseBody
    public Iterable<Customer> listCustomers() {
        return customerRepo.findAll();
    }
    
    @RequestMapping
    public String hello() {
        return "redirect:/customers";
    }
}
