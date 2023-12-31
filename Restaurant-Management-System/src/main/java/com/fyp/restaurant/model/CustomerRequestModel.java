package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequestModel {

    @NotNull
    @Size(max=50, message = "Name must be lesser than 50 characters")
    private String name;

    @NotNull
    @Size(max=255)
    @Pattern(regexp="[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}",
            message = "Email format invalid!")
    private String email;

    @NotNull
    @Pattern(regexp="(01\\d-\\d{7}$)",
            message = "Contact number format invalid!")
    private String contactNumber;

    @NotNull
    @Size(max=255)
    private String password;


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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
