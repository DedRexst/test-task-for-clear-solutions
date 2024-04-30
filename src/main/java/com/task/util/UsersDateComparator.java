package com.task.util;

import com.task.dtos.response.GetUserDTO;

import java.util.Comparator;

public class UsersDateComparator implements Comparator<GetUserDTO> {
    @Override
    public int compare(GetUserDTO o1, GetUserDTO o2) {
        return o1.birthDate().compareTo(o2.birthDate());
    }
}
