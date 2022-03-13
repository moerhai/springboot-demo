package com.mohai.one.springbootrestdocs.domain;

import javax.validation.constraints.*;

/**
 * 使用Bean Validation 2.0规范
 * 用注解来描述约束
 */
public class User {

    @NotBlank
    private Long id;
    @NotNull(message = "username can not be null")
    private String username;
    @Min(6)
    @Max(11)
    private String phone;
    @NotNull(message = "email can not be null")
    @NotEmpty
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
