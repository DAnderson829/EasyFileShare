package com.EasyFileSwap;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUsername(String username);

    Optional<User> findEmail(String email);
}
