package com.azda.broadcast.repository;

import com.azda.broadcast.model.UserPrivilages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrivilagesRepository extends JpaRepository<UserPrivilages, Long> {
    List<UserPrivilages> findByDeletedAtIsNull();
}
