package com.parking.serverP.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {

	private Integer id;
	private Integer userId;
	private String start;
	private String departure;
	private Double price;
}
