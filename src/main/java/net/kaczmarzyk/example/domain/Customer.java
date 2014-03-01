package net.kaczmarzyk.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    
    @Id @GeneratedValue
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private Date registrationDate;
    
    
    public Customer() {
    }
    
    public Customer(String firstName, String lastName, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
}
