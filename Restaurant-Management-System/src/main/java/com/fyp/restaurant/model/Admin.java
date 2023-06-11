package com.fyp.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="admin",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "adminId")
        })
public class Admin {

    @Id
    @Column(name="adminId", length=256, nullable = false, unique=true)
    private UUID adminId;

    @Column(name="name", length=50, nullable=false)
    private String name;

    @Email
    @Column(name="email", length=256, nullable=false)
    private String email;

    @Column(name="password", length=50, nullable=false)
    private String password;

    @ElementCollection
    @Column(name="roles", nullable=false)
    private List<String> roles;

    public Admin(){

    }

    public Admin(UUID adminId, String name, String email, String password,
                 List<String> roles) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @PrePersist
    private void onCreate() {
        adminId = UUID.randomUUID();
    }

    public UUID getAdminId() {
        return adminId;
    }

    public void setAdminId(UUID adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
