package com.azda.broadcast.repository;

import com.azda.broadcast.model.Internals;
import com.azda.broadcast.model.Organizations;
import com.azda.broadcast.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalRepository extends JpaRepository<Internals, Long> {

    // Superadmin
    List<Internals> findByDeletedAtIsNull();

    // Admin
    List<Internals> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<Internals> findByIdAndDeletedAtIsNull(Long id);

//    Internals findByName(String name);
//
//    Boolean existsByName(String name);
}
