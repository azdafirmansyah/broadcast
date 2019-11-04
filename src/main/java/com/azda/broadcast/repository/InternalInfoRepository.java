package com.azda.broadcast.repository;

import com.azda.broadcast.model.InternalInfo;
import com.azda.broadcast.model.Internals;
import com.azda.broadcast.model.OrganizationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalInfoRepository extends JpaRepository<InternalInfo, Long> {

    // Superadmin
    List<InternalInfo> findByDeletedAtIsNull();

    // Admin
    List<InternalInfo> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<InternalInfo> findByIdAndDeletedAtIsNull(Long id);
//    List<InternalInfo> findByDeletedAtIsNull();
//    InternalInfo findByKey(String key);
}
