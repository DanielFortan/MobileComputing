package com.parking.serverP.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.serverP.dto.UserDTO;
import com.parking.serverP.mapper.UserMapper;
import com.parking.serverP.model.User;
import com.parking.serverP.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;

	public UserDTO register(UserDTO userDTO) {
		User user = userMapper.mapUserDto(userDTO);
		User savedUser = userRepository.save(user);
		return userMapper.mapUser(savedUser);
	}

	public UserDTO authenticate(UserDTO userDTO) {
		User user = userRepository.findTopByEmail(userDTO.getEmail());
		if(user == null) {
			return null;
		}
		
		UserDTO mappedUserDTO = userMapper.mapUser(user);
		if(mappedUserDTO.haveSameCredentials(userDTO)) {
			return mappedUserDTO;
		}
		
		return null;
		
	}

	public UserDTO get(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepository.findById(id);
		if(optional.isPresent()) {
			return userMapper.mapUser(optional.get());
		}
		return null;
	}
}