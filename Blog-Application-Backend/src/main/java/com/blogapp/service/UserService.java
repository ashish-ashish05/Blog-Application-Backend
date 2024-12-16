package com.blogapp.service;

import com.blogapp.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO userDTO, String userType);
    UserDTO createUser (UserDTO userDTO);
    UserDTO updateUser (UserDTO userDTO, Integer userId);
    UserDTO getUserById (Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);

}
