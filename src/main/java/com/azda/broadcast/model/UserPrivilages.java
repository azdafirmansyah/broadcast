package com.azda.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user_privilages")
@ApiModel(description = "All Detail Information about User Privilages. ")
public class UserPrivilages {

    @ApiModelProperty(notes = "User Privilages ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "User Id")
    @Column(name = "user_id")
    private Long user_id;

    @ApiModelProperty(notes = "Organization Id")
    @Column(name = "organization_id")
    private Long organization_id;

    @ApiModelProperty(notes = "Internal Id")
    @Column(name = "internal_id")
    private Long internal_id;

    @ApiModelProperty(notes = "External Id")
    @Column(name = "external_id")
    private Long external_id;

    @ApiModelProperty(notes = "Role Id")
    @Column(name = "role_id")
    private Long role_id;

    @ApiModelProperty(notes = "User Created At")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(notes = "User Created By")
    @Column(name = "created_by")
    private Long createdBy;

    @ApiModelProperty(notes = "User Updated At")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updatedAt;

    @ApiModelProperty(notes = "User Updated By")
    @Column(name = "updated_by")
    private Long updatedBy;

    @ApiModelProperty(notes = "User Deleted At")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "deleted_at")
    private Date deletedAt;

    @ApiModelProperty(notes = "User Deleted By")
    @Column(name = "deleted_by")
    private Long deletedBy;

    @ManyToOne
    @JoinColumn
    private Users users;

    public UserPrivilages(){

    }

    public UserPrivilages(Long id, Long user_id, Long organization_id, Long internal_id, Long external_id, Long role_id, Date createdAt, Long createdBy, Date updatedAt, Long updatedBy, Date deletedAt, Long deletedBy, Users users) {
        this.id = id;
        this.user_id = user_id;
        this.organization_id = organization_id;
        this.internal_id = internal_id;
        this.external_id = external_id;
        this.role_id = role_id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
