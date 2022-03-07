package com.basics.java.loan.bean;

public class Review {
	private int reviewId;
	private int userId;
	private String email;
	private int loanId;
	private int rating;
	private String feedback;
	public Review(int reviewId, String email, int loanId, int rating, String feedback,int userId) {
		super();
		this.userId = userId;
		this.email = email;
		this.loanId = loanId;
		this.rating = rating;
		this.feedback = feedback;
		this.reviewId=reviewId;
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
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	
	
	
	
	

}
