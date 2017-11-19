package net.kaczmarzyk.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Customer {
    
    @Id @GeneratedValue
    private Long id;
    
    private String gender;
    
    private String firstName;
    
    private String lastName;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date registrationDate;
    
    private boolean deleted;
    
    
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
    
    public void delete() {
    	this.deleted = true;
    }
    
    public boolean isDeleted() {
		return deleted;
	}
}
