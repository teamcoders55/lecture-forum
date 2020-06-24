package com.refl3xn.DiscussionForum.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private int commentId;
	
	@Column(name = "post_id")
	private int postId;
	
	@Column(name="comment")
	private String commentText;
	
	@Transient
	private String replyCommentText;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "parent_id")
	private Integer parentId;
	private Timestamp createdOn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable=false, updatable=false)
	private User user;
	
	@OneToMany(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
	private List<Comment> replyComments;

	public Comment() {	
	}
	
	public Comment(int postId, String commentText, Integer parentId) {
		super();
		this.postId = postId;
		this.commentText = commentText;
		this.parentId = parentId;
	}

	public List<Comment> getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(List<Comment> replyComments) {
		this.replyComments = replyComments;
	}
	
	public String getReplyCommentText() {
		return replyCommentText;
	}

	public void setReplyCommentText(String replyCommentText) {
		this.replyCommentText = replyCommentText;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	
}
