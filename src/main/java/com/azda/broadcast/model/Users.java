package com.azda.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@ApiModel(description = "All Detail Information about Users. ")
public class Users {

    @ApiModelProperty(notes = "Users ID Auto Generate")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "User Name")
    @Column(name = "username", nullable = false, unique = true)
    @JsonIgnore
    private String username;

    @ApiModelProperty(notes = "User Password")
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    //    @NotNull(message = "Please Provide Email")
    @ApiModelProperty(notes = "User Email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ApiModelProperty(notes = "User Full Name")
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @ApiModelProperty(notes = "User Phone Number")
    @Column(name = "phone", nullable = false)
    private String phone;

    @ApiModelProperty(notes = "User Address")
    @Column(name = "address")
    private String address;

    @ApiModelProperty(notes = "User Tokens")
    @Column(name = "token")
    private String token;

    @ApiModelProperty(notes = "User Data")
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

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private Set<UserPrivilages> userPrivilages;

    public Users() {

    }

    public Users(Long id, String username, String password, String email, String fullname, String phone, String address, String token, String data, Date createdAt, Long createdBy, Date updatedAt, Long updatedBy, Date deletedAt, Long deletedBy, Set<UserPrivilages> userPrivilages) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.token = token;
        this.data = data;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
        this.userPrivilages = userPrivilages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Set<UserPrivilages> getUserPrivilages() {
        return userPrivilages;
    }

    public void setUserPrivilages(Set<UserPrivilages> userPrivilages) {
        this.userPrivilages = userPrivilages;
    }
}
