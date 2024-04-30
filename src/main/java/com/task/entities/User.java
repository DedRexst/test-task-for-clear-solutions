package com.task.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity{
    @NotBlank(message = "Email is missing")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @NotBlank(message = "First name is missing")
    @Pattern(regexp = "^(?=.*[a-zA-Z])\\w{3,30}$",
            message = "First name must contain at least one letter and can contain numbers or underscore from 3 to 30 symbols")
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @NotBlank(message = "Last name is missing")
    @Pattern(regexp = "^(?=.*[a-zA-Z])\\w{3,30}$",
            message = "Last name must contain at least one letter and can contain numbers or underscore from 3 to 30 symbols")
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @NotNull(message = "Birth date is missing")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Size(max = 100, message = "Address must be lesser than 100 characters")
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 15, message = "Phone number must be lesser than 15 characters")
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    public User() {

    }
}
