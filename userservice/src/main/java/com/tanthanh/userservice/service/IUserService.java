package com.tanthanh.userservice.service;

import java.util.List;

import com.tanthanh.userservice.data.Role;
import com.tanthanh.userservice.data.User;
import com.tanthanh.userservice.model.UserDTO;

public interface IUserService {
	List<User> getAllUser();
	List<Role> getAllRole();
	User saveUser(User user);
	Role saveRole(Role role);
	void addRole(String username,String roleName);
	UserDTO login(String username,String password);
	UserDTO validateToken(String token);
}
