package com.azda.broadcast.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@ApiModel(description = "All Detail Information about Roles. ")
public class Roles {

    @ApiModelProperty(notes = "Role ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Role Name")
    @Column(name = "name", nullable = false)
    @NotNull(message = "Please provide role name")
    private String name;

    @ApiModelProperty(notes = "Role Description")
    @Column(name = "description", nullable = false)
    @NotNull(message = "Please provide role description")
    private String description;

    public Roles() {

    }

    public Roles(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "Roles [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}
