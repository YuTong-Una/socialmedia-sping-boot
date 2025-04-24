package com.una.esunbank.socialmedia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.una.esunbank.socialmedia.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	Optional<Users> findByUserId(Integer userId);
	Optional<Users> findByEmail(String email);
    Optional<Users> findByPhoneNumber(String phoneNumber);
    
}
