/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author khalil
 */
public class User {
   private int id, phone ;
    private String username, email, address ,password, gender, image_filename ;
    private String[] roles ;

    public User(int id, int phone, String username, String email, String address, String password, String gender, String image_filename) {
        this.id = id;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
        this.gender = gender;
        this.image_filename = image_filename;
        this.roles = roles;
    }

    public User( int phone, String username, String email, String address, String password, String gender, String image_filename) {
        
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
        this.gender = gender;
        this.image_filename = image_filename;
    }

    public User( int phone, String username, String email, String address, String password, String gender, String image_filename, String[] roles) {
        
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
        this.gender = gender;
        this.image_filename = image_filename;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", phone=" + phone + ", username=" + username + ", email=" + email + ", address=" + address + ", password=" + password + ", gender=" + gender + ", image_filename=" + image_filename + ", roles=" + roles + '}';
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage_filename() {
        return image_filename;
    }

    public void setImage_filename(String image_filename) {
        this.image_filename = image_filename;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    
}
