package com.azda.broadcast.repository;

import com.azda.broadcast.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    // Superadmin
    List<Notifications> findByDeletedAtIsNull();

    // Admin
    List<Notifications> findByCreatedByAndDeletedAtIsNull(Long createdBy);

    // Users
    List<Notifications> findByIdAndDeletedAtIsNull(Long id);

    @Query(value = "select n.* from notifications n " +
            "where n.published_at is not NULL " +
            "and n.started_at <= CURRENT_TIMESTAMP() " +
            "and (n.finished_at >= CURRENT_TIMESTAMP() or n.finished_at is NULL) ", nativeQuery = true)
    List<Notifications> findAllNotification();

    @Query(value = "select n.* from notifications n " +
            "where n.published_at is not NULL " +
            "and n.started_at <= CURRENT_TIMESTAMP() " +
            "and (n.finished_at >= CURRENT_TIMESTAMP() or n.finished_at is NULL) and n.organization_id = ?1 ", nativeQuery = true)
    List<Notifications> findAllNotificationWithOrganizationId(Long organizationId);

    @Query(value = "select n.* from notifications n " +
            "where n.published_at is not NULL " +
            "and n.started_at <= CURRENT_TIMESTAMP() " +
            "and (n.finished_at >= CURRENT_TIMESTAMP() or n.finished_at is NULL) and n.internal_id = ?1 ", nativeQuery = true)
    List<Notifications> findAllNotificationWithInternalId(Long internalId);

    @Query(value = "select n.* from notifications n " +
            "where n.published_at is not NULL " +
            "and n.started_at <= CURRENT_TIMESTAMP() " +
            "and (n.finished_at >= CURRENT_TIMESTAMP() or n.finished_at is NULL) and n.external_id = ?1 ", nativeQuery = true)
    List<Notifications> findAllNotificationWithExternalId(Long externalId);

    @Query(value = "select n.* from notifications n " +
            "where n.published_at is not NULL " +
            "and n.started_at <= CURRENT_TIMESTAMP() " +
            "and (n.finished_at >= CURRENT_TIMESTAMP() or n.finished_at is NULL) and n.user_id like %:keyword% ", nativeQuery = true)
    List<Notifications> findAllNotificationWithUserId(String keyword);

//    and n.user_id like '%|" + str(tokens.get('id')) + "|%') "
}
