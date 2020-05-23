package com.parking.serverP.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String start;
	private String departure;
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
