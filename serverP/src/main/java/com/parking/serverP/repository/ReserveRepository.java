package com.parking.serverP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.serverP.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer>{

	List<Reserve> findAllByUserId(Integer id);

}
