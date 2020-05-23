package com.example.parking.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Integer id;
    private Integer userId;
    private String start;
    private String departure;
    private Double price;
}
