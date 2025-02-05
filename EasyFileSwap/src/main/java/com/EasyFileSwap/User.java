package com.EasyFileSwap;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;	
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true, length = 16)
	private String username;
	
	@Column(nullable = false)
	private String hashPassword;
	
	@Column(nullable = false)
	private LocalDateTime accountCreationDate;
	
	@Column(nullable = false)
	private boolean accountStatus;
	
	public User() {}
	
	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.hashPassword = new BCryptPasswordEncoder().encode(password);
		accountCreationDate = LocalDateTime.now();
		accountStatus = true;
	}
	
	public boolean checkPassword(String password) {
        return new BCryptPasswordEncoder().matches(password, this.hashPassword);
    }
	
	public void setPassword(String newPassword) {
        this.hashPassword = new BCryptPasswordEncoder().encode(newPassword);
    }
	
	
	public Long getId() {return Id;}
	public void setEmail(String email) {this.email = email;}
	public String getEmail() {return email;}
	public void setUserName(String username) {this.username = username;}
	public String getUserName() {return username;}
	public LocalDateTime getAccountCreationDate() {return accountCreationDate;}
	public boolean getAccountStatus() {return accountStatus;}
}
