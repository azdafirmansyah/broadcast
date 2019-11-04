package com.azda.broadcast.repository;

import com.azda.broadcast.model.ExternalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalInfoRepository extends JpaRepository<ExternalInfo, Long> {

    // Superadmin
    List<ExternalInfo> findByDeletedAtIsNull();

    // Admin
    List<ExternalInfo> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<ExternalInfo> findByIdAndDeletedAtIsNull(Long id);
}
