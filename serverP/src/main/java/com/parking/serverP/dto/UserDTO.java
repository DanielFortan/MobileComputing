package com.parking.serverP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Integer id;
	private String email;
	private String password;
	
	public boolean haveSameCredentials(UserDTO userDTO) {
		return userDTO.getEmail().equals(email) && userDTO.getPassword().equals(password);
	}
	
}