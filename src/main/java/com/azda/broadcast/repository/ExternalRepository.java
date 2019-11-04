package com.azda.broadcast.repository;

import com.azda.broadcast.model.Externals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalRepository extends JpaRepository<Externals, Long> {

    // Superadmin
    List<Externals> findByDeletedAtIsNull();

    // Admin
    List<Externals> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<Externals> findByIdAndDeletedAtIsNull(Long id);
}
