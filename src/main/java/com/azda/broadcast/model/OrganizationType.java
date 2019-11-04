package com.azda.broadcast.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organization_type")
@ApiModel(description = "All Detail Information about Organizations Type. ")
public class OrganizationType {

    @ApiModelProperty(notes = "Organizations Type ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Organizations Type Name")
    @Column(name = "type", nullable = false)
    @NotNull(message = "Please Provide Organizations Type Name")
    private String type;

    public OrganizationType() {

    }

    public OrganizationType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrganizationType [id=" + id + ", type=" + type + "]";
    }
}
