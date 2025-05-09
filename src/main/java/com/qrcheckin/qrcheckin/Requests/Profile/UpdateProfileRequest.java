package com.qrcheckin.qrcheckin.Requests.Profile;

import com.qrcheckin.qrcheckin.Annotations.PasswordMatches;
import com.qrcheckin.qrcheckin.Annotations.UniqueEmail;
import com.qrcheckin.qrcheckin.Interfaces.HasMatchingPassword;
import com.qrcheckin.qrcheckin.Models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@PasswordMatches()
public class UpdateProfileRequest implements HasMatchingPassword {

    public UpdateProfileRequest(){}

    public UpdateProfileRequest(User user){
        this();
        if(user == null){
            return;
        }
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
    }

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname must be at most 50 characters")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @UniqueEmail(excludeAuthEmail = true)
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String password_repeat;

    private boolean delete_image;

    private MultipartFile image;

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean getDelete_image(){ return delete_image; }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPassword_repeat() {
        return password_repeat;
    }

    public MultipartFile getImage() {
        return image;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword_repeat(String password_repeat) {
        this.password_repeat = password_repeat;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setDelete_image(boolean delete_image) {
        this.delete_image = delete_image;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password_repeat='" + password_repeat + '\'' +
                ", image=" + (image != null ? image.getOriginalFilename() : "null") +
                '}';
    }
}
