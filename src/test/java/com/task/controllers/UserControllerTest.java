package com.task.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.request.RangeDatesDTO;
import com.task.dtos.response.AddUserDTO;
import com.task.dtos.response.GetUserDTO;
import com.task.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.task.utils.TestConstants.BASE_URL;
import static com.task.utils.TestConstants.BASE_URL_PLUS_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @Test
    @DisplayName("Should return status, status code and new user url")
    void addUser() throws Exception {
        AddAndPutUserDTO addUserDTO = new AddAndPutUserDTO("email@email.com", "firstName",
                "LastName", LocalDate.now(), null, null);
        AddUserDTO addUserResponseDTO = new AddUserDTO(HttpStatus.CREATED.value(), HttpStatus.CREATED, BASE_URL_PLUS_ID);

        when(userService.addUser(addUserDTO)).thenReturn(addUserResponseDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(jsonPath("$.statusCode").value(addUserResponseDTO.statusCode()))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.location").value(addUserResponseDTO.location()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw exception that user to young")
    void addUserThrowExceptionUserToYoung() throws Exception {
        AddAndPutUserDTO addUserDTO = new AddAndPutUserDTO("email@email.com", "firstName",
                "LastName", LocalDate.of(2009, 4, 30), null, null);

        when(userService.addUser(addUserDTO)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is to young"));

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(ResponseStatusException.class, result.getResolvedException());
                    assertEquals(HttpStatus.BAD_REQUEST,
                            ((ResponseStatusException) result.getResolvedException()).getStatusCode());
                });
    }

    @Test
    @DisplayName("Should return OK status")
    void patchUser() throws Exception {
        PatchUserDTO patchUserDTO = new PatchUserDTO("email@email.com", "firstName",
                "LastName", LocalDate.now(), "Lviv", "999999999");

        mockMvc.perform(patch(BASE_URL_PLUS_ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patchUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return OK status")
    void putUser() throws Exception {
        AddAndPutUserDTO putUserDTO = new AddAndPutUserDTO("email@email.com", "firstName",
                "LastName", LocalDate.now(), null, null);

        mockMvc.perform(put(BASE_URL_PLUS_ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(putUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return OK status")
    void deleteUser() throws Exception {
        mockMvc.perform(delete(BASE_URL_PLUS_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return exception date from bigger than to")
    void getUsersByBirtDateFromBiggerThanTo() throws Exception {
        RangeDatesDTO rangeDatesDTO = new RangeDatesDTO(
                LocalDate.of(2009,4,30),
                LocalDate.of(2006, 4, 30));
        Map<String, String> errors = new HashMap<>();
        errors.put("timestamp", String.valueOf(LocalDateTime.now()));
        errors.put("status", "400");
        errors.put("error", "From bigger than to");

        when(userService.getUsersByBirtDate(rangeDatesDTO))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "From bigger than to"));

        mockMvc.perform(get(BASE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(rangeDatesDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertInstanceOf(ResponseStatusException.class, result.getResolvedException());
                    assertEquals(HttpStatus.BAD_REQUEST,
                    ((ResponseStatusException) result.getResolvedException()).getStatusCode());
                });
    }
    @Test
    @DisplayName("Should return OK status, and empty list")
    void getUsersByBirtDate() throws Exception {
        RangeDatesDTO rangeDatesDTO = new RangeDatesDTO(
                LocalDate.of(2004,4,30),
                LocalDate.of(2006, 4, 30));
        List<GetUserDTO> getUserDTOS = new ArrayList<>();

        when(userService.getUsersByBirtDate(rangeDatesDTO))
                .thenReturn(getUserDTOS);

        mockMvc.perform(get(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rangeDatesDTO)))
                .andExpect(status().isOk());
    }
}