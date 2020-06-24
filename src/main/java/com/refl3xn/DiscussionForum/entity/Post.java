package com.refl3xn.DiscussionForum.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;


@Entity(name = "posts")

public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id")
	private int postId;

	@NotNull
	@Size(min = 10, max = 250, message = "Subject size should be in between 10 and 150")
	private String subject;
	
	@NotNull
	@Size(min = 10, max = 700, message = "Description size should be in between 10 and 700")
	private String description;
	
	@NotNull
	private String tags;
	
	@Column(name = "user_id")
	private int userId;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable=false, updatable=false)
	private User user;
	
	
	@OneToMany(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "post_id", referencedColumnName = "post_id")	
	@Where(clause = "parent_id is null")
	private List<Comment> Comments;

	public Post() {
	}

	public List<Comment> getComments() {
		return Comments;
	}

	public void setComments(List<Comment> comments) {
		Comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
}
