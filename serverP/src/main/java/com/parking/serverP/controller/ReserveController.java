package com.parking.serverP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.serverP.dto.ReserveDTO;
import com.parking.serverP.service.ReserveService;
import com.parking.serverP.util.UriUtil;

@RestController
@RequestMapping(value = "/reserve")
public class ReserveController {
	
	@Autowired
	private ReserveService reserveService;

	@PostMapping("/{id}")
	public ResponseEntity<ReserveDTO> register(@PathVariable Integer id, @RequestBody ReserveDTO reserveDTO) {
		ReserveDTO registeredReserve = reserveService.reserve(reserveDTO, id);
		return ResponseEntity.created(UriUtil.buildUri("/{id}", registeredReserve.getId())).body(registeredReserve);
		
	}
	
	@GetMapping("/user/{id}")
	public List<ReserveDTO> register(@PathVariable Integer id) {
		List<ReserveDTO> reserves = reserveService.findAll(id);
		return reserves;
	}	
	
}
