package com.azda.broadcast.repository;

import com.azda.broadcast.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    // Superadmin
    List<Users> findByDeletedAtIsNull();

    // Admin
    List<Users> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<Users> findByIdAndDeletedAtIsNull(Long id);

    Users findByUsernameAndPasswordAndDeletedAtIsNull(String username, String password);

    Users findByToken(String token);

//    Users findByIdAndToken(Long id, String token);

    Boolean existsByUsername(String username);

//    Boolean existsByUsernameAndPasswordAndDeletedAtIsNull(String username, String password);

}
