package com.parking.serverP.mapper;

import org.springframework.stereotype.Component;

import com.parking.serverP.dto.UserDTO;
import com.parking.serverP.model.User;

@Component
public class UserMapper {

	public User mapUserDto(UserDTO userDTO) {
		User user = new User();

		user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());

		return user;
	}
	
	public UserDTO mapUser(User user) {
		return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
	}

}