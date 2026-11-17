package com.tourist.tourist;

import jakarta.persistence.*;

@Entity
@Table(name = "tourists")
public class Tourist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String country;
    private String destination;

    public Tourist() {}
    public Tourist(String fullName, String email, String country, String destination) {
        this.fullName = fullName;
        this.email = email;
        this.country = country;
        this.destination = destination;
    }
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
