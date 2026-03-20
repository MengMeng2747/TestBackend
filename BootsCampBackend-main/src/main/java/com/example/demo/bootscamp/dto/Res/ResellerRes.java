package com.example.demo.bootscamp.dto.Res;

import java.time.LocalDateTime;

public class ResellerRes {

    private Integer id;
    private String  name;
    private String  email;
    private String  phone;
    private String  role;
    private String  status;
    private String  address;
    private String  shopName;   // ← join จาก shops table
    private String  createdAt;  // ← ISO string จาก users.created_at

    public ResellerRes() {}

    public ResellerRes(Integer id, String name, String email, String phone,
                       String role, String status, String address,
                       String shopName, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.phone     = phone;
        this.role      = role;
        this.status    = status;
        this.address   = address;
        this.shopName  = shopName;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
    }

    public Integer getId()       { return id; }
    public String  getName()     { return name; }
    public String  getEmail()    { return email; }
    public String  getPhone()    { return phone; }
    public String  getRole()     { return role; }
    public String  getStatus()   { return status; }
    public String  getAddress()  { return address; }
    public String  getShopName() { return shopName; }
    public String  getCreatedAt(){ return createdAt; }

    public void setId(Integer id)          { this.id = id; }
    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setPhone(String phone)     { this.phone = phone; }
    public void setRole(String role)       { this.role = role; }
    public void setStatus(String status)   { this.status = status; }
    public void setAddress(String address) { this.address = address; }
    public void setShopName(String s)      { this.shopName = s; }
    public void setCreatedAt(String s)     { this.createdAt = s; }
}