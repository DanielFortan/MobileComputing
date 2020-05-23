package com.parking.serverP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.serverP.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findTopByEmail(String email);

}
