package com.tanthanh.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanthanh.userservice.data.Role;
import com.tanthanh.userservice.data.User;
import com.tanthanh.userservice.data.UserRepository;
import com.tanthanh.userservice.model.AddRoleToUser;
import com.tanthanh.userservice.model.UserDTO;
import com.tanthanh.userservice.service.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private IUserService userService;

	
	@GetMapping("/listUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	@GetMapping("/listRole")
	public List<Role> getAllRole(){
		return userService.getAllRole();
	}
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	@PostMapping("/addRoleToUser")
	public void addRoleToUser(@RequestBody AddRoleToUser model) {
		 userService.addRole(model.getUsername(), model.getRolename());
	}
	@PostMapping("/addRole")
	public Role addRole(@RequestBody Role role) {
		return userService.saveRole(role);
	}
	@PostMapping("/login")
	public UserDTO login(@RequestBody UserDTO dto) {
		return userService.login(dto.getUsername(), dto.getPassword());
	}
	@PostMapping("/validateToken")
	public UserDTO validateToken(@RequestBody UserDTO dto) {
		return userService.validateToken(dto.getToken());
	}
	
	
	@GetMapping
	public String homePage() {
		return "Day la trang chu";
	}
}
