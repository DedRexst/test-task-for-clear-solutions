package com.task.controllers;

import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.request.RangeDatesDTO;
import com.task.dtos.response.AddUserDTO;
import com.task.dtos.response.GetUserDTO;
import com.task.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public AddUserDTO addUser(@RequestBody @Valid AddAndPutUserDTO addUserDTO) {
        return userService.addUser(addUserDTO);
    }

    @PatchMapping("/{id}")
    public void patchUser(@RequestBody @Valid PatchUserDTO patchUserDTO, @PathVariable UUID id) {
        userService.patchUser(id, patchUserDTO);
    }

    @PutMapping("/{id}")
    public void putUser(@RequestBody @Valid AddAndPutUserDTO addAndPutUserDTO, @PathVariable UUID id) {
        userService.putUser(id, addAndPutUserDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<GetUserDTO> getUsersByBirtDate(@RequestBody RangeDatesDTO rangeDatesDTO) {
        return userService.getUsersByBirtDate(rangeDatesDTO);
    }

}
