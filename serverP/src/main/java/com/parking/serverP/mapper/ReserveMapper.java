package com.parking.serverP.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.parking.serverP.dto.ReserveDTO;
import com.parking.serverP.model.Reserve;

@Component
public class ReserveMapper {

	public Reserve mapReserveDTO(ReserveDTO reserveDTO) {
		Reserve reserve = new Reserve();
		reserve.setStart(reserveDTO.getStart());
		reserve.setDeparture(reserveDTO.getDeparture());
		reserve.setPrice(reserveDTO.getPrice());
		return reserve;
	}

	public ReserveDTO mapReserve(Reserve reserve) {
		return new ReserveDTO(reserve.getId(), reserve.getUser().getId(), reserve.getStart(), reserve.getDeparture(), reserve.getPrice());
	}

	public List<ReserveDTO> mapReserves(List<Reserve> reserves) {
		List<ReserveDTO> reservesDto = new ArrayList<ReserveDTO>();
		
		for(Reserve reserve : reserves) {
			reservesDto.add(mapReserve(reserve));
		}
		
		return reservesDto;
	}

}
