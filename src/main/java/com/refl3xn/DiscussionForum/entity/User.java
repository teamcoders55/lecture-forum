package com.refl3xn.DiscussionForum.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.awt.Image;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userId;
	
	@NotNull(message = "please enter your email")
	@Email(message = "email is invalid")
	private String email;
	
	// @NotNull(message = "please enter your name")
	private String name;
	
	@NotNull(message = "please enter password")
	@Length(min = 5, message = "password shoud be of atleast 5 characters")
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	
	private String createdBy;
	private Timestamp createdOn;
	private String updatedBy;
	private Timestamp updatedOn;
	private String role;
	
	@Transient
	private Image image;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private List<Post> posts;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private List<Comment> comments;
	
	public User() {	
	}
	
	public void viewUserDetails() {
		System.out.println("Id: " + this.userId);
		System.out.println("Name: " + this.name);
		System.out.println("Email: " + this.email);
		System.out.println("Password: " + this.password);
		System.out.println("CreatedBy: " + this.createdBy);
		System.out.println("CreatedOn: " + this.createdOn);
		System.out.println("UpdatedBy: " + this.updatedBy);
		System.out.println("UpdatedOn: " + this.updatedOn);
		System.out.println("Role: " + this.role);
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
}
