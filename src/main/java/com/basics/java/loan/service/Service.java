package com.basics.java.loan.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.basics.java.loan.bean.AdminModel;
import com.basics.java.loan.bean.DocumentModel;
import com.basics.java.loan.bean.LoanApplicantModel;
import com.basics.java.loan.bean.LoanDetails;
import com.basics.java.loan.bean.LoginModel;
import com.basics.java.loan.bean.UserModel;
import com.basics.java.loan.bean.profile;
import com.basics.java.loan.bean.reason;
import com.basics.java.loan.bean.repayment;
import com.basics.java.loan.repository.Repository;

@org.springframework.stereotype.Service
public class Service {

	@Autowired
	Repository rep;
	
	private final Path rootLocation = Paths.get("uploads");

	public void saveUser(UserModel user) {
		// TODO Auto-generated method stub
		rep.saveUser(user);
	}

	public boolean isUserPresent(LoginModel data) {
		// TODO Auto-generated method stub
		return rep.isUserPresent(data);
	}

	public void saveAdmin(UserModel user) {
		// TODO Auto-generated method stub
		
		rep.saveAdmin(user);
	}

	public boolean isAdminPresent(LoginModel data) {
		// TODO Auto-generated method stub
		return rep.isAdminPresent(data);
	}

	public int takeUserId(LoginModel data) {
		// TODO Auto-generated method stub
		return rep.takeUserId(data);
	}

	public void sendEmail(String email) {
	
		rep.sendEmail(email);
		
	}

	public boolean checkOtp(String email, String otp) {
		// TODO Auto-generated method stub
		return rep.checkOtp(email,otp);
	}

	public boolean checkNumber(String number) {
		// TODO Auto-generated method stub
		return rep.checkNumber(number);
	}

	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return rep.checkEmail(email);
	}

	public boolean Username(String username) {
		// TODO Auto-generated method stub
		return rep.checkUsername(username);
	}

	public void updatePassword(LoginModel data) {
		
		rep.updatePassword(data);
		
	}

	public void store(MultipartFile file , String newFileName) {
		
		try {
			System.out.println(file.getOriginalFilename());
			
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH-mm-ss");
//			   LocalDateTime now = LocalDateTime.now();
//			   String currentDate=dtf.format(now);
//			   System.out.println(currentDate);
//			String newFileName="("+currentDate+")"+file.getOriginalFilename();

			System.out.println(rootLocation.toUri() + newFileName);
			Files.copy(file.getInputStream(), this.rootLocation.resolve(newFileName));

			System.out.println("this is from controller class..");
//			byte[] bytes = file.getBytes();
//			Path path = (Path) Paths.get(msg);
//			Files.write(path,bytes);

//			java.nio.file.Path path = Paths.get(msg);
//	        Files.deleteIfExists(path);
//	        InputStream in = file.getInputStream();
//	        Files.copy(in, path);

			// profileRepo.updateImage(Integer id,String path);
		} catch (Exception e) {
			System.out.println("this is from error conytroller...." + e);
			throw new RuntimeException("FAIL!");
		}
		
	}

	public String addLoan(LoanApplicantModel data, String userId) {
		// TODO Auto-generated method stub
		return rep.addLoan(data,userId);
	}

	public List<LoanDetails> getLoan(String userId) {
		// TODO Auto-generated method stub
		return rep.getLoan(userId);
	}

	public List<DocumentModel> getDocuments() {
		// TODO Auto-generated method stub
		return rep.getDocuments();
	}

	public void editLoan(LoanApplicantModel data, String loanId) {
		
		rep.editLoan(data,loanId);
		
	}

	public void deleteLoan(String loanId) {
		
		rep.deleteLoan(loanId);
		
	}

	public void deleteDocument(String documentId) {
		
		
		rep.deleteDocument(documentId);
		
	}

	public List<UserModel> getUser(String userId) {
		
		return rep.getUser(userId);
		
	}

	public List<LoanApplicantModel> getLoans(String userId) {
		// TODO Auto-generated method stub
		return rep.getLoans(userId);
	}

	public void addUser(profile pro) {
		
		rep.addUser(pro);
		
	}

	public List<profile> getProfile(String userId) {
		// TODO Auto-generated method stub
		return rep.getProfile(userId);
	}

	public void editUser(profile pro, String userId) {
		// TODO Auto-generated method stub
		rep.editUser(pro,userId);
	}

	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		rep.deleteUser(userId);
		
	}

	public List<LoanApplicantModel> getAllLoans() {
		// TODO Auto-generated method stub
		return rep.getAllLoans();
	}

	public List<AdminModel> getAllRequest() {
		// TODO Auto-generated method stub
		return rep.getAllRequest();
	}

	public void deleteRequest(String requestId) {
		// TODO Auto-generated method stub
		rep.deleteRequest(requestId);
		
	}

	public void addRequest(String requestId) {
		// TODO Auto-generated method stub
		rep.addRequest(requestId);
		
	}

	public void addReason(reason pro) {
		// TODO Auto-generated method stub
		
		rep.addReason(pro);
		
	}

	public List<LoanApplicantModel> getAllCheckedLoans() {
		// TODO Auto-generated method stub
		return rep.getAllCheckedLoans();
	}

	public List<reason> getAllReason() {
		// TODO Auto-generated method stub
		return rep.getAllReason();
	}

	public List<UserModel> getAllUser() {
		// TODO Auto-generated method stub
		return rep.getAllUser();
	}

	public void generateSchedule(String loanId) {
		// TODO Auto-generated method stub
		rep.generateSchedule(loanId);
		
	}

	public List<repayment> getSchedule(String loanId) {
		// TODO Auto-generated method stub
		return rep.getSchedule(loanId);
	}

	public List<LoanApplicantModel> getThisLoan(String loanId) {
		// TODO Auto-generated method stub
		return rep.getThisLoan(loanId);
	}
}
