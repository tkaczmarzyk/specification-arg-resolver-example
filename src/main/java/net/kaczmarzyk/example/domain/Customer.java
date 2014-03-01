package net.kaczmarzyk.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    
    @Id @GeneratedValue
    private Long id;
    
    private String gender;
    
    private String firstName;
    
    private String lastName;
    
    private Date registrationDate;
    
    
    public Customer() {
    }
    
    public Customer(String firstName, String lastName, String gender, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
    
    public String getGender() {
        return gender;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
}
