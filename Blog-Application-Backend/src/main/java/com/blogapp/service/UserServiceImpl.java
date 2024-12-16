package com.blogapp.service;

import com.blogapp.config.AppConstants;
import com.blogapp.dto.UserDTO;
import com.blogapp.entity.Role;
import com.blogapp.entity.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.repository.RoleRepository;
import com.blogapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO, String userType) {
        User user = this.modelMapper.map(userDTO, User.class);
        // encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // set role
        Role role = null;
        if(userType.equals("admin")){
            role = this.roleRepository.findById(AppConstants.ADMIN_USER).get();
        }
        if(userType.equals("normal")){
            role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        }

        user.getRoles().add(role);

        User newUser = this.userRepository.save(user);

        return this.modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.userDtoToUser(userDTO);
        User savedUser = userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepository.save(user);

        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        return this.userToUserDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users =  this.userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());

        return userDTOs;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);

    }

    private User userDtoToUser(UserDTO userDTO){
        User user = this.modelMapper.map(userDTO, User.class);

//        user.setId(userDTO.getId());
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());
        return user;
    }

    private UserDTO userToUserDto(User user){
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAbout(user.getAbout());
        return userDTO;
    }
}
