package com.azda.broadcast.repository;

import com.azda.broadcast.model.OrganizationInfo;
import com.azda.broadcast.model.Organizations;
import com.azda.broadcast.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationInfoRepository extends JpaRepository<OrganizationInfo, Long> {

    // Superadmin
    List<OrganizationInfo> findByDeletedAtIsNull();

    // Admin
    List<OrganizationInfo> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<OrganizationInfo> findByIdAndDeletedAtIsNull(Long id);


//    OrganizationInfo findByKey(String key);
//    Users findByName(String name);
//
//    Boolean existsByName(String name);
}
