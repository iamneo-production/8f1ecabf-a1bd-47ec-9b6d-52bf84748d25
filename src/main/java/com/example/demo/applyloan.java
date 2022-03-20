package com.example.demo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Table;

import javax.persistence.Id;

import java.util.UUID;

import javax.persistence.Column;
@Entity
@Table(name="ApplyLoan2")

public class applyloan {
	@Id
	@GeneratedValue
	 
	@Column(name="loanID") private UUID loanId;
	@Column(name="name") private String name;
	@Column(name="address") private String address;
	
	@Column(name="mobile",length = 10) private String mobile;
	
	@Column(name="aadhar",length=12) private String aadhar;
	@Column(name="pan") private String pan;
	@Column(name="salary") private Integer salary;
	@Column(name="amount") private Integer amount;
	@Column(name="reypaymentmonths") private Integer reypaymentmonths;
	
	
	
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public UUID getLoanId() {
		return loanId;
	}
	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	

	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getReypaymentmonths() {
		return reypaymentmonths;
	}
	public void setReypaymentmonths(Integer reypaymentmonths) {
		this.reypaymentmonths = reypaymentmonths;
	}
	
	
	

}