package com.task.services.impl;

import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.request.RangeDatesDTO;
import com.task.dtos.response.AddUserDTO;
import com.task.dtos.response.GetUserDTO;
import com.task.entities.User;
import com.task.mappers.UserMapper;
import com.task.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void addUser() {
        AddAndPutUserDTO addAndPutUserDTO =
                new AddAndPutUserDTO("email@email.com", "Bob","Stallone", LocalDate.of(2004, 4, 30),"Lviv", null);
        User user = new User();
        setField(user, "id", UUID.randomUUID());
        user.setAddress(addAndPutUserDTO.address());
        user.setEmail(addAndPutUserDTO.email());
        user.setFirstName(addAndPutUserDTO.firstName());
        user.setBirthDate(addAndPutUserDTO.birthDate());
        user.setLastName(addAndPutUserDTO.lastName());

        when(userMapper.dtoToEntity(addAndPutUserDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        AddUserDTO addUserDTO = userService.addUser(addAndPutUserDTO);

        assertEquals(addUserDTO, new AddUserDTO("/api/v1/user/" + user.getId()));
    }

    @Test
    void patchUser() {
        PatchUserDTO patchUserDTO =
                new PatchUserDTO("email@email.com", "Bob","Stallone", LocalDate.of(2009, 4, 30),"Lviv", null);
        User user = new User();
        setField(user, "id", UUID.randomUUID());
        user.setAddress(patchUserDTO.address());
        user.setEmail(patchUserDTO.email());
        user.setFirstName(patchUserDTO.firstName());
        user.setBirthDate(patchUserDTO.birthDate());
        user.setLastName(patchUserDTO.lastName());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(ResponseStatusException.class,() -> {userService.patchUser(user.getId(), patchUserDTO);});
    }

    @Test
    void putUser() {
        AddAndPutUserDTO addAndPutUserDTO =
                new AddAndPutUserDTO("email@email.com", "Bob","Stallone", LocalDate.of(2009, 4, 30),"Lviv", null);
        User user = new User();
        setField(user, "id", UUID.randomUUID());
        user.setAddress(addAndPutUserDTO.address());
        user.setEmail(addAndPutUserDTO.email());
        user.setFirstName(addAndPutUserDTO.firstName());
        user.setBirthDate(addAndPutUserDTO.birthDate());
        user.setLastName(addAndPutUserDTO.lastName());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(ResponseStatusException.class,() -> {userService.putUser(user.getId(), addAndPutUserDTO);});
    }

    @Test
    void deleteUser() {
        User user = new User();
        setField(user, "id", UUID.randomUUID());

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,() -> {userService.deleteUser(user.getId());});
    }

    @Test
    void getUsersByBirtDate() {
        RangeDatesDTO rangeDatesDTO = new RangeDatesDTO(
                LocalDate.of(2004,4,30),
                LocalDate.of(2004, 4, 30));
        List<User> users = new ArrayList<>();
        User bob = new User("email@email.com", "Bob","Stallone", LocalDate.of(2004, 4, 30),"Lviv", null);
        users.add(bob);

        when(userRepository.findAllByBirthDateGreaterThanEqualAndBirthDateLessThanEqual(rangeDatesDTO.from(), rangeDatesDTO.to())).thenReturn(users);
        when(userMapper.entityToDTO(bob)).thenReturn(new GetUserDTO("email@email.com", "Bob","Stallone", LocalDate.of(2004, 4, 30),"Lviv", null));

        List<GetUserDTO> getUserDTOS = userService.getUsersByBirtDate(rangeDatesDTO);

        assertTrue(getUserDTOS.get(0).birthDate().equals(LocalDate.of(2004, 4, 30)));
    }
}