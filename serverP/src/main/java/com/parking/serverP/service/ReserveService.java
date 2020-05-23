package com.parking.serverP.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.serverP.dto.ReserveDTO;
import com.parking.serverP.mapper.ReserveMapper;
import com.parking.serverP.model.Reserve;
import com.parking.serverP.model.User;
import com.parking.serverP.repository.ReserveRepository;
import com.parking.serverP.repository.UserRepository;

@Service
public class ReserveService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReserveRepository reserveRepository;

	@Autowired
	private ReserveMapper reserveMapper;
	
	
	public ReserveDTO reserve(ReserveDTO reserveDTO, Integer userId) {
		User user = userRepository.findById(userId).get();
		Reserve reserve = reserveMapper.mapReserveDTO(reserveDTO);
		reserve.setUser(user);
		Reserve saved = reserveRepository.save(reserve);
		return reserveMapper.mapReserve(saved);
	}


	public List<ReserveDTO> findAll(Integer id) {
		List<Reserve> reserves = reserveRepository.findAllByUserId(id);
		List<ReserveDTO> reservesDTO = reserveMapper.mapReserves(reserves);
		return reservesDTO ;
	}

}
