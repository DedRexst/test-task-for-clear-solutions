package com.task.dtos.response;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public record AddUserDTO(int statusCode, HttpStatus status, String location) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddUserDTO that)) return false;
        return statusCode == that.statusCode && status == that.status && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, status, location);
    }
}
