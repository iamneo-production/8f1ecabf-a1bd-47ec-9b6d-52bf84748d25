package com.basics.java.loan.bean;

public class reason {

	private int loanId;
	private String reason;
	private int adminId;
	private String date;
	private String email;
	public reason(int loanId, String reason, int adminId, String date, String email) {
		super();
		this.loanId = loanId;
		this.reason = reason;
		this.adminId = adminId;
		this.date = date;
		this.email=email;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
