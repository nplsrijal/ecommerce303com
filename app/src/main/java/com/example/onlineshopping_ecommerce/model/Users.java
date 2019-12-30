package com.example.onlineshopping_ecommerce.model;



public class Users {


    private String Id,Fullname,EmailId,RoleId,Image,Phone,Location;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Users(String id, String fullname, String emailId, String roleId, String image, String phone, String location) {
        Id = id;
        Fullname = fullname;
        EmailId = emailId;
        RoleId = roleId;
        Image = image;
        Phone = phone;
        Location = location;
    }
}
