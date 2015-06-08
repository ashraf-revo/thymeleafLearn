package thymeleafLearn.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by ashraf on 6/3/15.
 */
@Entity
public class user implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 50)
    private String name;
    @Email
    @Column(length = 50)
    private String email;
    @Column(length = 60)
    private String password;
    @Transient
    private Set<GrantedAuthority> authorities;
    @Column(length = 50)
    private String imagePath;
    @Transient
    private MultipartFile file;
    public user() {
    }

    public user(String email,String password,String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public user(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Set<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("role_user"));
        }
        return authorities;
    }

    public user setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public user setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getId() {
        return id;
    }

    public user setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public user setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public user setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public MultipartFile getFile() {
        return file;
    }

    public user setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public user setFile(MultipartFile file) {
        this.file = file;
        setImagePath(file.getOriginalFilename());
        return this;
    }

    @Override
    public String toString() {
        return id + "  " + name + "  " + email;
    }
}
