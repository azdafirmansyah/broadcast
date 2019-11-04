package com.azda.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.dom4j.Branch;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organization_info")
@ApiModel(description = "All Detail Information about Organization Info. ")
public class OrganizationInfo {

    @ApiModelProperty(notes = "Organization Info ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Organization Info Key")
    @Column(name = "key_data")
    private String key;

    @ApiModelProperty(notes = "Organization Info Label")
    @Column(name = "label")
    private String label;

    @ApiModelProperty(notes = "Organization Info Value")
    @Column(name = "value")
    private String value;

    @ApiModelProperty(notes = "Organization Info Data")
    @Column(name = "data",columnDefinition = "TEXT")
    private String data;

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

    @ApiModelProperty(notes = "Organization Id")
    @Column(name = "organization_id")
    private Long organization_id;

    @ManyToOne
    @JoinColumn
    private Organizations organizations;

    public OrganizationInfo() {

    }

    public OrganizationInfo(Long id, String key, String label, String value, String data, Date createdAt,
                            Long createdBy, Date updatedAt, Long updatedBy, Date deletedAt, Long deletedBy,
                            Long organization_id, Organizations organizations) {
        this.id = id;
        this.key = key;
        this.label = label;
        this.value = value;
        this.data = data;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.organization_id = organization_id;
        this.organizations = organizations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Long getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }

    public Organizations getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Organizations organizations) {
        this.organizations = organizations;
    }
}
