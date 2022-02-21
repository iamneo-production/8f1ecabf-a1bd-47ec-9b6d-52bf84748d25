package com.basics.java.loan.bean;

public class repayment {
	private int loanId;
	private int installmentNo;
	private String emi;
	private String fine;
	private String total;
	private String amountPaid;
	private String dueDate;
	private String paidDate;
	public repayment(int loanId, int installmentNo, String emi, String fine, String total, String amountPaid,
			String dueDate, String paidDate) {
		super();
		this.loanId = loanId;
		this.installmentNo = installmentNo;
		this.emi = emi;
		this.fine = fine;
		this.total = total;
		this.amountPaid = amountPaid;
		this.dueDate = dueDate;
		this.paidDate = paidDate;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getInstallmentNo() {
		return installmentNo;
	}
	public void setInstallmentNo(int installmentNo) {
		this.installmentNo = installmentNo;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	
	

}
