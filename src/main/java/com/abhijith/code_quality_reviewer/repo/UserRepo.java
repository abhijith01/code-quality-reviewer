package com.abhijith.code_quality_reviewer.repo;

import com.abhijith.code_quality_reviewer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String fullName);

}
