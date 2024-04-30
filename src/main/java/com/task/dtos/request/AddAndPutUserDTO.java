package com.task.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AddAndPutUserDTO(@Email(message = "Email should be valid") @NotBlank(message = "Email is missing")
                      String email,
                               @Pattern(message = "First name must contain at least one letter and can contain numbers or underscore from 3 to 30 symbols",
                              regexp = "^(?=.*[a-zA-Z])\\w{3,30}$") @NotBlank(message = "First name is missing")
                      String firstName,
                               @Pattern(message = "Last name must contain at least one letter and can contain numbers or underscore from 3 to 30 symbols",
                              regexp = "^(?=.*[a-zA-Z])\\w{3,30}$") @NotBlank(message = "Last name is missing")
                      String lastName,
                               @NotNull(message = "Birth date is missing")
                      LocalDate birthDate,
                               @Size(message = "Address must be lesser than 100 characters", max = 100)
                      String address,
                               @Size(message = "Phone number must be lesser than 15 characters", max = 15)
                      String phoneNumber
) {
}