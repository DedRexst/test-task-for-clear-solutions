package com.task.services.impl;

import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.request.RangeDatesDTO;
import com.task.dtos.response.AddUserDTO;
import com.task.dtos.response.GetUserDTO;
import com.task.entities.User;
import com.task.mappers.UserMapper;
import com.task.repositories.UserRepository;
import com.task.services.UserService;
import com.task.util.UsersDateComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${min-age}")
    private int minAge = 18;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AddUserDTO addUser(AddAndPutUserDTO addUserDTO) {
        User user = userMapper.dtoToEntity(addUserDTO);
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() >= minAge) {// I try to refactor this peace of code in to method, but he has some differences
            user = userRepository.save(user);
            return new AddUserDTO("/api/v1/user/" + user.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is to young");
        }
    }

    @Override
    @Transactional
    public void patchUser(UUID id, PatchUserDTO patchUserDTO) {
        User user = findById(id);
        if (patchUserDTO.birthDate() == null || Period.between(patchUserDTO.birthDate(), LocalDate.now()).getYears() >= minAge) {// I try to refactor this peace of code in to method, but he has some differences
            userMapper.updateEntityFromDto(patchUserDTO, user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New user's age is to small");
        }
    }

    @Override
    @Transactional
    public void putUser(UUID id, AddAndPutUserDTO addAndPutUserDTO) {
        User user = findById(id);

        if (Period.between(addAndPutUserDTO.birthDate(), LocalDate.now()).getYears() >= minAge) {
            userMapper.updateEntityFromDto(addAndPutUserDTO, user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New user's age is to small");
        }
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetUserDTO> getUsersByBirtDate(LocalDate from, LocalDate to) {
        if (from != null && to != null) {
            if (from.compareTo(to) <= 0) {
                return convertListUserToGetUserDTO(userRepository.findAllByBirthDateGreaterThanEqualAndBirthDateLessThanEqual(from,
                        to));
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From bigger than to");
        } else if (from != null) {
            return convertListUserToGetUserDTO(userRepository.findAllByBirthDateGreaterThanEqual(from));
        } else if (to != null){
            return convertListUserToGetUserDTO(userRepository.findAllByBirthDateLessThanEqual(to));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From and to is null");
    }

    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User with this ID doesn't exist"));
    }

    private List<GetUserDTO> convertListUserToGetUserDTO(List<User> users){
        List<GetUserDTO> getUserDTOS = new ArrayList<>();
        for (User user:
             users) {
            getUserDTOS.add(userMapper.entityToDTO(user));
        }
        getUserDTOS.sort(new UsersDateComparator());
        return getUserDTOS;
    }
}
