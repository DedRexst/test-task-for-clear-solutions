package com.task.repositories;

import com.task.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByBirthDateGreaterThanEqualAndBirthDateLessThanEqual(LocalDate from, LocalDate to);

    List<User> findAllByBirthDateGreaterThanEqual(LocalDate from);

    List<User> findAllByBirthDateLessThanEqual(LocalDate to);
}
