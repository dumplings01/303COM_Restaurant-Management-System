package com.fyp.restaurant.model;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class AdminRequestModel {

    @NotNull
    @Size(max=50, message = "Name must be lesser than 50 characters")
    private String name;

    @NotNull
    @Size(max=256, message = "Email must be lesser than 50 characters")
    @Pattern(regexp="[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}",
            message = "Email format invalid!")
    private String email;

    @NotNull
    @Size(max=256, message = "Password must be lesser than 50 characters")
    private String password;

    @ElementCollection
    @NotNull
    private List<String> roles;

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
