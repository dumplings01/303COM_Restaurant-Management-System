package com.fyp.restaurant.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestModel {

    @NotNull
    @Size(max=255)
    @Pattern(regexp="[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}",
            message = "Email format invalid!")
    private String email;

    @NotNull
    @Size(max=255)
    private String password;

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
}
