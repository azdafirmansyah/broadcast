package com.azda.broadcast.repository;

import com.azda.broadcast.model.Organizations;
import com.azda.broadcast.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organizations, Long> {

    // Superadmin
    List<Organizations> findByDeletedAtIsNull();

    // Admin
    List<Organizations> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<Organizations> findByIdAndDeletedAtIsNull(Long id);

//    Users findByName(String name);
//
//    Boolean existsByName(String name);
}
