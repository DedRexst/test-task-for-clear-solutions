package com.task.dtos.response;

import java.time.LocalDate;

public record GetUserDTO(String email,String firstName, String lastName, LocalDate birthDate,
                         String address, String phoneNumber) {
}
