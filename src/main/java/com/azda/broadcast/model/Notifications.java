package com.azda.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
@ApiModel(description = "All Detail Notification about Notification Message. ")
public class Notifications {
    @ApiModelProperty(notes = "Notification ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Organization Id")
    @Column(name = "organization_id")
    private Long organization_id;

    @ApiModelProperty(notes = "Internal Id")
    @Column(name = "internal_id")
    private Long internal_id;

    @ApiModelProperty(notes = "External Id")
    @Column(name = "external_id")
    private Long external_id;

    @ApiModelProperty(notes = "User Id")
    @Column(name = "user_id",columnDefinition = "TEXT")
    private String user_id;

    @ApiModelProperty(notes = "Notification Message")
    @Column(name = "message")
    private String message;

    @ApiModelProperty(notes = "User Published At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "published_at")
    private Date publishedAt;

    @ApiModelProperty(notes = "User Started At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "started_at")
    private Date startedAt;

    @ApiModelProperty(notes = "User Finished At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "finished_at")
    private Date finishedAt;

    @ApiModelProperty(notes = "User Created At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(notes = "User Created By")
    @Column(name = "created_by")
    private Long createdBy;

    @ApiModelProperty(notes = "User Updated At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updatedAt;

    @ApiModelProperty(notes = "User Updated By")
    @Column(name = "updated_by")
    private Long updatedBy;

    @ApiModelProperty(notes = "User Deleted At")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "deleted_at")
    private Date deletedAt;

    @ApiModelProperty(notes = "User Deleted By")
    @Column(name = "deleted_by")
    private Long deletedBy;

    public Notifications () {

    }

    public Notifications(Long id, Long organization_id, Long internal_id, Long external_id, String user_id, String message, Date publishedAt, Date startedAt, Date finishedAt, Date createdAt, Long createdBy, Date updatedAt, Long updatedBy, Date deletedAt, Long deletedBy) {
        this.id = id;
        this.organization_id = organization_id;
        this.internal_id = internal_id;
        this.external_id = external_id;
        this.user_id = user_id;
        this.message = message;
        this.publishedAt = publishedAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }

    public Long getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(Long internal_id) {
        this.internal_id = internal_id;
    }

    public Long getExternal_id() {
        return external_id;
    }

    public void setExternal_id(Long external_id) {
        this.external_id = external_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }
}
