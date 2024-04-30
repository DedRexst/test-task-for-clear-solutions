package com.task.services;

import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.request.RangeDatesDTO;
import com.task.dtos.response.AddUserDTO;
import com.task.dtos.response.GetUserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    AddUserDTO addUser(AddAndPutUserDTO addUserDTO);

    void patchUser(UUID id, PatchUserDTO patchUserDTO);

    void putUser(UUID id, AddAndPutUserDTO addAndPutUserDTO);

    void deleteUser(UUID id);

    List<GetUserDTO> getUsersByBirtDate(RangeDatesDTO rangeDatesDTO);
}
