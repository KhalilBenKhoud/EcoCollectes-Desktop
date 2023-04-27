/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

/**
 *
 * @author hamza
 */
public class User {
    
    private int id;
    private String username;
    private String email;
    private String address;
    private String[] roles;
    private String password;
    private String image_filename;
    private String gender;

    public User(int id, String username, String email, String address, String[] roles, String password, String image_filename, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.roles = roles;
        this.password = password;
        this.image_filename = image_filename;
        this.gender = gender;
    }

    public User(String username, String email, String address, String[] roles, String password, String image_filename, String gender) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.roles = roles;
        this.password = password;
        this.image_filename = image_filename;
        this.gender = gender;
    }

    public User() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage_filename() {
        return image_filename;
    }

    public void setImage_filename(String image_filename) {
        this.image_filename = image_filename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return username;
    }
    
    
    
    
    
}
