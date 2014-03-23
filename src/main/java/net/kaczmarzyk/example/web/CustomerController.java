package net.kaczmarzyk.example.web;

import net.kaczmarzyk.example.domain.Customer;
import net.kaczmarzyk.example.repo.CustomerRepository;
import net.kaczmarzyk.spring.data.jpa.domain.DateBefore;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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

    @RequestMapping(value = "", params = { "firstName" })
    @ResponseBody
    public Iterable<Customer> filterCustomersByFirstName(
            @Spec(path = "firstName", spec = Like.class) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "lastName" }) // gender param is optional
    @ResponseBody
    public Iterable<Customer> filterCustomersByLastNameAndGender(
            @And({@Spec(path = "lastName", spec = Like.class),
                @Spec(path = "gender", spec = Like.class)}) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "name" })
    @ResponseBody
    public Iterable<Customer> filterCustomersByName(
            @Or({@Spec(path = "firstName", params = "name", spec = Like.class),
                @Spec(path = "lastName", params = "name", spec = Like.class)}) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "registeredBefore" })
    @ResponseBody
    public Iterable<Customer> findCustomersRegisteredBefore(
            @Spec(path = "registrationDate", params = "registeredBefore", config = "yyyy-MM-dd", spec = DateBefore.class) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "registeredAfter", "registeredBefore" })
    @ResponseBody
    public Iterable<Customer> findCustomersByRegistrationDate(
            @Spec(path = "registrationDate", params = {"registeredAfter", "registeredBefore"}, config = "yyyy-MM-dd", spec = DateBetween.class) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "gender", "name" })
    @ResponseBody
    public Iterable<Customer> findCustomersByGenderAndName(
            @And(value = { @Spec(path = "gender", spec = Like.class)},
                and = { @Or({
                    @Spec(params="name", path="firstName", spec=Like.class),
                    @Spec(params="name", path="lastName", spec=Like.class)})}) Specification<Customer> spec) {

        return customerRepo.findAll(spec);
    }
    
    @RequestMapping(value = "", params = { "registeredBefore", "name" })
    @ResponseBody
    public Iterable<Customer> findCustomersByRegistrationDateAndName(
            @Spec(path="registrationDate", params="registeredBefore", spec=DateBefore.class) Specification<Customer> registrationDateSpec,
            @Or({@Spec(params="name", path="firstName", spec=Like.class),
                @Spec(params="name", path="lastName", spec=Like.class)}) Specification<Customer> nameSpec) {

        Specification<Customer> spec = Specifications.where(registrationDateSpec).and(nameSpec);
        
        return customerRepo.findAll(spec);
    }
}
