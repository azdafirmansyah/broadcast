package com.azda.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "organizations")
@ApiModel(description = "All Detail Information about Organizations. ")
public class Organizations {

    @ApiModelProperty(notes = "Organization ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Organization Name")
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(notes = "Organization Description")
    @Column(name = "description", nullable = false)
    private String description;

    @ApiModelProperty(notes = "Organization Phone Number")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(notes = "Organization Address")
    @Column(name = "address")
    private String address;

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

    @OneToMany(mappedBy = "organization_id", cascade = CascadeType.ALL)
    private Set<OrganizationInfo> organizationInfos;

    public Organizations() {

    }

    public Organizations(Long id, String name, String description, String phone, String address, Date createdAt, Long createdBy, Date updatedAt, Long updatedBy, Date deletedAt, Long deletedBy, Set<OrganizationInfo> organizationInfos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.organizationInfos = organizationInfos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<OrganizationInfo> getOrganizationInfos() {
        return organizationInfos;
    }

    public void setOrganizationInfos(Set<OrganizationInfo> organizationInfos) {
        this.organizationInfos = organizationInfos;
    }

}
