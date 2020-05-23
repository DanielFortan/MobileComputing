package com.parking.serverP.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.parking.serverP.dto.UserDTO;
import com.parking.serverP.service.UserService;
import com.parking.serverP.util.UriUtil;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
		UserDTO authUser = userService.authenticate(userDTO);
		if(authUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(authUser);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
		UserDTO registeredUser = userService.register(userDTO);
		return ResponseEntity.created(UriUtil.buildUri("/{id}", registeredUser.getId())).body(registeredUser);
		
	}
	

	@GetMapping("/{id}")
	public UserDTO user(@PathVariable Integer id) {
		UserDTO userDTO = userService.get(id);
		return userDTO;
	}
	
	
	
}