package com.basics.java.loan.repository;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

//import com.basics.java.loan.controller.Controller.DocumentModelMapper;
import com.basics.java.loan.DateCheck;
import com.basics.java.loan.passwordEncryption;
import com.basics.java.loan.bean.AdminModel;
import com.basics.java.loan.bean.DocumentModel;
import com.basics.java.loan.bean.LoanApplicantModel;
import com.basics.java.loan.bean.LoanDetails;
import com.basics.java.loan.bean.LoginModel;
import com.basics.java.loan.bean.MapModel;
import com.basics.java.loan.bean.Otp;
import com.basics.java.loan.bean.UserModel;
import com.basics.java.loan.bean.profile;
import com.basics.java.loan.bean.reason;
import com.basics.java.loan.bean.repayment;

@org.springframework.stereotype.Repository
public class Repository {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	JavaMailSender javaMailSender;

	public void saveUser(UserModel user) {
		// TODO Auto-generated method stub
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(user.getPassword());
		
		String sql="insert into usermodel(email,password,username,mobile,userRole) values(:email,:password,:username,:mobile,:userRole)";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email",user.getEmail())
				.addValue("password", encryptpassword)
				.addValue("username", user.getUsername())
				.addValue("mobile", user.getNumber())
				.addValue("userRole", user.getUserRole());
		
		jdbcTemplate.update(sql, parameterSource);
		
	}
	
	class UserModelMapper implements RowMapper<UserModel> {

		@Override
		public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			UserModel user = new UserModel(rs.getInt("userId"),rs.getString("email"), rs.getString("password"), rs.getString("username"),
				 rs.getString("mobile"), rs.getString("userRole"));

			return user;
		}

	}
	
	class AdminModelMapper implements RowMapper<AdminModel> {

		@Override
		public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			AdminModel admin = new AdminModel(rs.getInt("requestId"),rs.getString("email"), rs.getString("password"),
				 rs.getString("mobile"), rs.getString("userRole"));

			return admin;
		}

	}
	
	class OtpMapper implements RowMapper<Otp> {
		@Override
		public Otp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Otp o = new Otp(rs.getInt("id"), rs.getString("mail"), rs.getString("otps"), rs.getString("date"));
			return o;
		}
	}
	
	class DocumentModelMapper implements RowMapper<DocumentModel> {
		@Override
		public DocumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			DocumentModel o = new DocumentModel(rs.getInt("documentId"), rs.getString("documentType"), rs.getString("documentAddress"));
			return o;
		}
	}
	
	class MapModelMapper implements RowMapper<MapModel> {
		@Override
		public MapModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			MapModel o = new MapModel(rs.getInt("mapId"), rs.getInt("userId"), rs.getInt("loanId"), rs.getInt("documentId"), rs.getString("dateAdded"));
			return o;
		}
	}
	
	class ProfileMapper implements RowMapper<profile> {
		@Override
		public profile mapRow(ResultSet rs, int rowNum) throws SQLException {
			profile pro = new profile(rs.getInt("profileId"), rs.getInt("userId"), rs.getString("name"), rs.getString("mobile"), rs.getString("email"),rs.getString("address"));
			return pro;
		}
	}
	
	class RepaymentMapper implements RowMapper<repayment> {
		@Override
		public repayment mapRow(ResultSet rs, int rowNum) throws SQLException {
			repayment repay = new repayment(rs.getInt("loanId"), rs.getInt("installmentNo"), rs.getString("emi"), rs.getString("fine"), rs.getString("total"),rs.getString("amountpaid"),
					rs.getString("dueDate"),rs.getString("paidDate"));
			return repay;
		}
	}
	
	class ReasonMapper implements RowMapper<reason> {
		@Override
		public reason mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			reason rea=null;
			
			String sql="select * from usermodel";
			List<UserModel> list=jdbcTemplate.query(sql, new UserModelMapper());
			for(UserModel userModel: list)
			{
				if(userModel.getUserId()==rs.getInt("adminId"))
				{
					 rea = new reason(rs.getInt("loanId"), rs.getString("reason"), rs.getInt("adminId"), rs.getString("date"),userModel.getEmail());
					
				}
			}
			
			return rea;
			
			
		}
	}
	
	class LoanModelMapper implements RowMapper<LoanApplicantModel> {

		@Override
		public LoanApplicantModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String sql="select * from mapmodel";
			List<MapModel> list=jdbcTemplate.query(sql, new MapModelMapper());
			ArrayList<String> docId=new ArrayList<String>();
			
			for(MapModel mapModel: list)
			{
				if(mapModel.getLoanId()==rs.getInt("loanId"))
				{
					docId.add(String.valueOf(mapModel.getDocumentId()));
				}
			}
			String documentIds[]=new String[docId.size()];
			for(int i=0;i<docId.size();i++)
			{
				documentIds[i]=docId.get(i);
			}
			
			
			LoanApplicantModel loan = new LoanApplicantModel(rs.getInt("loanId"),rs.getString("loanType"), rs.getString("applicantName"), rs.getString("applicantAddress"),
				 rs.getString("applicantMobile"), rs.getString("applicantEmail"), rs.getString("applicantAadhar"),
				rs.getString("applicantPan"), rs.getString("applicantSalary"), rs.getString("loanAmount"), rs.getString("repaymentMonths"),
				documentIds, rs.getString("status"), rs.getInt("userId"));

			return loan;
		}

	}
	
	class LoanDetailsMapper implements RowMapper<LoanDetails > {

		@Override
		public LoanDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String sql="select * from loanmodel";
			List<LoanApplicantModel> list=jdbcTemplate.query(sql, new LoanModelMapper());
			LoanDetails loan=null;
			String sql1="select * from mapmodel";
			List<MapModel> lists=jdbcTemplate.query(sql1, new MapModelMapper());
			
			for(LoanApplicantModel applicantModel: list)
			{
				for(MapModel mapModel: lists)
				{
					if(applicantModel.getLoanId()==rs.getInt("loanId") && mapModel.getLoanId()==rs.getInt("loanId"))
					{
						 loan = new LoanDetails(applicantModel.getLoanId(), applicantModel.getLoantype(),
							applicantModel.getApplicantName(),applicantModel.getApplicantAddress(),applicantModel.getApplicantMobile(),
							applicantModel.getApplicantEmail(),applicantModel.getApplicantAadhaar(),applicantModel.getApplicantPan(),
							applicantModel.getApplicantSalary(),applicantModel.getLoanAmountRequired(),applicantModel.getLoanRepaymentMonths(),
							mapModel.getUserId(), mapModel.getDocumentId(),applicantModel.getStatus());
				
						
					}
				}
				
			}
			
			
			return loan;
		}

	}

	public boolean isUserPresent(LoginModel data) {
		
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(data.getPassword());
		String sql="select * from usermodel where email=:email and password=:password";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email", data.getEmail())
				.addValue("password", encryptpassword);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource, new UserModelMapper());
		
		boolean flag=false;
		
		if(!list.isEmpty())
		{
			flag=true;
		}
		
		return flag;
	}

	public void saveAdmin(UserModel user) {
		
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(user.getPassword());
		
		String sql="insert into requestadmin(email,password,mobile,userRole) values(:email,:password,:mobile,:userRole)";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email",user.getEmail())
				.addValue("password", encryptpassword)
				.addValue("mobile", user.getNumber())
				.addValue("userRole", user.getUserRole());
		
		jdbcTemplate.update(sql, parameterSource);
		
		
	}

	public boolean isAdminPresent(LoginModel data) {
		
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(data.getPassword());
		
		String sql="select * from usermodel where email=:email and password=:password";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email", data.getEmail())
				.addValue("password", encryptpassword);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource, new UserModelMapper());
		
		boolean flag=false;
		
		if(!list.isEmpty())
		{
			flag=true;
		}
		
		return flag;
	}

	public int takeUserId(LoginModel data) {
		int id=0;
	
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(data.getPassword());
		String sql="select * from usermodel where email=:email and password=:password";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email", data.getEmail())
				.addValue("password", encryptpassword);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource, new UserModelMapper());
		
	
		if(!list.isEmpty())
		{
			for(UserModel model: list)
			{
				id=model.getUserId();
			}
		}
			
		
		return id;
	}

	public void sendEmail(String email) {
		
		try {
			System.out.println(email);
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(email);

			int a = 0;
			do {
				a = (int) (Math.random() * 1000000);
			} while (String.valueOf(a).length() < 6);

			String otpValue = String.valueOf(a);
			msg.setSubject("Email Verification");
			msg.setText("Hey,\nYour OTP is " + a + "." + "\nNote:- It will valid only for 5 mins.");
			javaMailSender.send(msg);
//	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//	        LocalDateTime now = LocalDateTime.now();
//	         String str=dtf.format(now);
			if (!checkEmail1(email)) {
				String sql = "insert into otp(mail,otps) values(:user_mail,:otp)";
				SqlParameterSource parameterSource = new MapSqlParameterSource("user_mail", email).addValue("otp",
						otpValue);
				jdbcTemplate.update(sql, parameterSource);
			} else {
				String sql = "update otp set otps=:ot where mail=:email";
				SqlParameterSource parameterSource = new MapSqlParameterSource("ot", otpValue).addValue("email", email);
				jdbcTemplate.update(sql, parameterSource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done");

		
		
	}
	
	public boolean checkEmail1(String email) {
		boolean flag = false;
		String sql = "select * from otp where mail=:email";
		SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
		List<Otp> list = jdbcTemplate.query(sql, parameterSource, new OtpMapper());
		if (!list.isEmpty()) {
			flag = true;
		}
		return flag;

	}

	public boolean checkOtp(String email, String otp) {
		
		
		DateCheck dc = new DateCheck();
		String sql1 = "select * from otp";
		List<Otp> list1 = jdbcTemplate.query(sql1, new OtpMapper());
		ArrayList<String> regTime = new ArrayList<String>();
		if (!list1.isEmpty()) {
			for (Otp otp2 : list1) {
				regTime.add(otp2.getDate());
			}
			for (int i = 0; i < regTime.size(); i++) {
				if (!dc.validDate(regTime.get(i))) {
					String query = "delete from otp where date=:dates";
					SqlParameterSource parameterSource = new MapSqlParameterSource("dates", regTime.get(i));
					jdbcTemplate.update(query, parameterSource);
				}
			}

		}

		String sql = "select * from otp where mail=:email";
		SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
		List<Otp> list = jdbcTemplate.query(sql, parameterSource, new OtpMapper());
		if (!list.isEmpty()) {
			String str = "";
			String prevDate = "";
			for (Otp otp2 : list) {
				str = otp2.getOtp();
				prevDate = otp2.getDate();
			}

			System.out.println("prev date: " + prevDate);

//			
			if (dc.validDate(prevDate)) {
				if (otp.equals(str)) {
					return true;
				} else {
					return false;
				}
			}

		}

		return false;
		
	
	}

	public boolean checkNumber(String number) {
		
		boolean flag=false;
		String sql="select * from usermodel where mobile=:mobile";
		SqlParameterSource parameterSource=new MapSqlParameterSource("mobile",number);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource,new UserModelMapper());
		if(!list.isEmpty())
		{
			flag=true;
		}
			
		return flag;
	}

	public boolean checkEmail(String email) {
		boolean flag=false;
		String sql="select * from usermodel where email=:email";
		SqlParameterSource parameterSource=new MapSqlParameterSource("email",email);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource,new UserModelMapper());
		if(!list.isEmpty())
		{
			flag=true;
		}
			
		return flag;
		
		
	}

	public boolean checkUsername(String username) {
		// TODO Auto-generated method stub
		
		boolean flag=false;
		String sql="select * from usermodel where username=:username";
		SqlParameterSource parameterSource=new MapSqlParameterSource("username",username);
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource,new UserModelMapper());
		if(!list.isEmpty())
		{
			flag=true;
		}
			
		return flag;
		
		
	}

	public void updatePassword(LoginModel data) {
		
		passwordEncryption pc=new passwordEncryption();
		String encryptpassword=pc.encryptPassword(data.getPassword());
		
		String sql="update usermodel set password=:password where email=:email";
		SqlParameterSource parameterSource=new MapSqlParameterSource("password",encryptpassword)
				.addValue("email", data.getEmail());
		
		jdbcTemplate.update(sql, parameterSource);
		
		
	}

	public String addLoan(LoanApplicantModel data, String userId) {
		
		int a = 0;
		do {
			a = (int) (Math.random() * 1000000000);
		} while (String.valueOf(a).length() < 9 && checkLoanId(a)==false);
		
		String sql="insert into loanmodel(loanId,loanType,applicantName,applicantAddress,applicantMobile,applicantEmail,applicantAadhar,applicantPan,applicantSalary,loanAmount,repaymentMonths,status,userId) values(:loanId,:loanType,:applicantName,:applicantAddress,:applicantMobile,:applicantEmail,:applicantAadhar,:applicantPan,:applicantSalary,:loanAmount,:repaymentMonths,:status,:userId)";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",a).addValue("loanType", "Personal")
				.addValue("applicantName", data.getApplicantName()).addValue("applicantAddress", data.getApplicantAddress())
				.addValue("applicantMobile", data.getApplicantMobile()).addValue("applicantEmail", data.getApplicantEmail()).addValue("applicantAadhar", data.getApplicantAadhaar()).addValue("applicantPan", data.getApplicantPan())
				.addValue("applicantSalary", data.getApplicantSalary()).addValue("loanAmount", data.getLoanAmountRequired()).addValue("repaymentMonths", data.getLoanRepaymentMonths()).addValue("status", "In Process").addValue("userId", Integer.parseInt(userId));
		
		jdbcTemplate.update(sql, parameterSource);
		
		String Id[]=data.getDocumentIds();
		
		for(int i=0;i<Id.length;i++)
		{
		
		String sql1="insert into mapmodel(userId,loanId,documentId) values(:userId,:loanId,:documentId)";
		SqlParameterSource parameterSource2=new MapSqlParameterSource("userId",Integer.parseInt(userId))
				.addValue("loanId", a).addValue("documentId", Integer.parseInt(Id[i]));
		jdbcTemplate.update(sql1, parameterSource2);
		
		}
		String loans=String.valueOf(a);
		
		return loans;
	}
	public boolean checkLoanId(int value)
	{
		boolean flag=false;
		
		String sql="select * from loanmodel where loanId=:loanId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",value);
		List<LoanApplicantModel> list =jdbcTemplate.query(sql, parameterSource, new LoanModelMapper());
		if(!list.isEmpty())
		{
			flag=true;
		}
		
		return flag;
	}

	public List<LoanDetails> getLoan(String userId) {

//		String sql="select * from loanmodel where loanId=:loanId";
//		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId));
//		List<LoanApplicantModel> list=jdbcTemplate.query(sql, parameterSource,new LoanModelMapper());
		
		String sql="SELECT loanmodel.* , mapmodel.userId,mapmodel.documentId,mapmodel.status FROM loanmodel JOIN mapmodel ON loanmodel.loanId=mapmodel.loanId where mapmodel.userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",userId);
		
		List<LoanDetails> list=jdbcTemplate.query(sql,parameterSource,new LoanDetailsMapper());
		
		
		return list;
	}

	public List<DocumentModel> getDocuments() {
		
		
		String sql="select * from documentmodel";
		List<DocumentModel> list=jdbcTemplate.query(sql, new DocumentModelMapper());
		
		return list;
	}

	public void editLoan(LoanApplicantModel data, String loanId) {
		
		
		String sql="update loanmodel set applicantName=:applicantName,applicantAddress=:applicantAddress,applicantMobile=:applicantMobile,applicantEmail=:applicantEmail,applicantAadhar=:applicantAadhar,applicantPan=:applicantPan,applicantSalary=:applicantSalary,loanAmount=:loanAmount,repaymentMonths=:repaymentMonths where loanId=:loanId";
		
		
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId))
				.addValue("applicantName", data.getApplicantName()).addValue("applicantAddress", data.getApplicantAddress())
				.addValue("applicantMobile", data.getApplicantMobile()).addValue("applicantEmail", data.getApplicantEmail()).addValue("applicantAadhar", data.getApplicantAadhaar()).addValue("applicantPan", data.getApplicantPan())
				.addValue("applicantSalary", data.getApplicantSalary()).addValue("loanAmount", data.getLoanAmountRequired()).addValue("repaymentMonths", data.getLoanRepaymentMonths());
		
		jdbcTemplate.update(sql, parameterSource);
		
		
	}

	public void deleteLoan(String loanId) {
		
		String sql="delete from loanmodel where loanId=:loanId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId));
		jdbcTemplate.update(sql, parameterSource);
		
		String sql1="select * from mapmodel where loanId=:loanId";
		List<MapModel> list=jdbcTemplate.query(sql1, parameterSource, new MapModelMapper());
		//int docId=0;
		ArrayList<Integer> docId=new ArrayList<Integer>();
		
		for(MapModel mapModel: list)
		{
			docId.add(mapModel.getDocumentId());
		}
		
		String sql2="delete from mapmodel where loanId=:loanId";
		jdbcTemplate.update(sql2, parameterSource);
		
		for(int i=0;i<docId.size();i++)
		{
		
		String sql3="select * from documentmodel where documentId=:documentId";
		SqlParameterSource parameterSource2=new MapSqlParameterSource("documentId",docId.get(i));
		
		List<DocumentModel> lists=jdbcTemplate.query(sql3, parameterSource2, new DocumentModelMapper());
		String fileName="";
		for(DocumentModel documentModel: lists)
		{
			fileName=documentModel.getDocumentupload();
		}
		
		String sql4="delete from documentmodel where documentId=:documentId";
		
		jdbcTemplate.update(sql4, parameterSource2);
		
		
		try  
		{      
			
		File f= new File("C:/Users/mashh/OneDrive/Documents/Mehta/Workspace/Loan/uploads/"+fileName);           //file to be delete  
		if(f.delete())                      //returns Boolean value  
		{  
		System.out.println(f.getName() + " deleted");   //getting and printing the file name  
		}  
		else  
		{  
		System.out.println("failed");  
		}  
		}  
		catch(Exception e)  
		{  
		e.printStackTrace();  
		}  
		
	 }
		
		
	}

	public void deleteDocument(String documentId) {
		
		
		String sql1="select * from documentmodel where documentId=:documentId";
		SqlParameterSource parameterSource2=new MapSqlParameterSource("documentId",Integer.parseInt(documentId));
		List<DocumentModel> list=jdbcTemplate.query(sql1, parameterSource2, new DocumentModelMapper());
		
		String oldFileName="";
		
		for(DocumentModel documentModel: list)
		{
			oldFileName=documentModel.getDocumentupload();
		}
		
		try  
		{      
			
		File f= new File("C:/Users/mashh/OneDrive/Documents/Mehta/Workspace/Loan/uploads/"+oldFileName);           //file to be delete  
		if(f.delete())                      //returns Boolean value  
		{  
		System.out.println(f.getName() + " deleted");   //getting and printing the file name  
		}  
		else  
		{  
		System.out.println("failed");  
		}  
		}  
		catch(Exception e)  
		{  
		e.printStackTrace();  
		}  
		
		String sql2="delete from documentmodel where documentId=:documentId";
		
		jdbcTemplate.update(sql2, parameterSource2);
		
		
//		String sql4="select * from mapmodel where documentId=:documentId";
//		
//		List<MapModel> Lists= jdbcTemplate.query(sql4, parameterSource2, new MapModelMapper());
//		int loanId=0;
//		
//		for(MapModel mapModel: Lists)
//		{
//			loanId=mapModel.getLoanId();
//		}
//		
//		String sql5="select * from mapmodel where loanId=:loanId";
//		SqlParameterSource parameterSource3 =new MapSqlParameterSource("loanId",loanId);
//		List<MapModel> loanList= jdbcTemplate.query(sql5, parameterSource3, new MapModelMapper());
		
//		if(loanList.size()>1)
//		{
		
		String sql3="delete from mapmodel where documentId=:documentId";
		jdbcTemplate.update(sql3, parameterSource2);
//		}
//		else
//		{
//			String sql6="update mapmodel set documentId=0 where documentId=:documentId";
//			jdbcTemplate.update(sql6, parameterSource2);
//		}
		
		
		
	}

	public List<UserModel> getUser(String userId) {
		
		String sql="select * from usermodel where userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId", Integer.parseInt(userId));
		List<UserModel> list= jdbcTemplate.query(sql, parameterSource, new UserModelMapper());
		
		
		return list;
	}

	
	public List<LoanApplicantModel> getLoans(String userId) {
	
//		String sql="select * from mapmodel where userId=:userId";
//		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",Integer.parseInt(userId));
//		List<MapModel> list= jdbcTemplate.query(sql, parameterSource, new MapModelMapper());
//		
//		ArrayList<Integer> List=new ArrayList<Integer>();
//		
//		for(MapModel mapModel: list)
//		{
//			if(!List.contains(mapModel.getLoanId()))
//			List.add(mapModel.getLoanId());
//		}
//		
//		
//		
//		List<LoanApplicantModel> lists= new ArrayList<>();
//		
//		
//		for(int i=0;i<List.size();i++)
//		{
//			String sql1="select * from loanmodel where loanId=:loanId";
//			SqlParameterSource parameterSource2=new MapSqlParameterSource("loanId",List.get(i));
//			List<LoanApplicantModel> myList=jdbcTemplate.query(sql1, parameterSource2, new LoanModelMapper());
//			if(myList!=null)
//			{
//				for(LoanApplicantModel applicantModel: myList)
//				{
//					lists.add(applicantModel);
//				}
//			}
//		}
//		
		
		String sql="select * from loanmodel where userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",Integer.parseInt(userId));
		List<LoanApplicantModel> lists= jdbcTemplate.query(sql, parameterSource, new LoanModelMapper());
		
		
		return lists;
	}

	public void addUser(profile pro) {
		
		String sql="insert into profile(userId,name,mobile,email,address) values(:userId,:name,:mobile,:email,:address)";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",pro.getUserId())
				.addValue("name", pro.getName()).addValue("mobile", pro.getNumber())
				.addValue("email", pro.getEmail()).addValue("address", pro.getAddress());
		
		jdbcTemplate.update(sql, parameterSource);
		
	}

	public List<profile> getProfile(String userId) {
		
		String sql="select * from profile where userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",Integer.parseInt(userId));
		List<profile> list=jdbcTemplate.query(sql, parameterSource, new ProfileMapper());
		
		return list;
	}

	public void editUser(profile pro, String userId) {
		
		String sql="update profile set name=:name,mobile=:mobile,email=:email,address=:address where userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("name",pro.getName())
				.addValue("mobile", pro.getNumber()).addValue("email", pro.getEmail())
				.addValue("address", pro.getAddress()).addValue("userId", Integer.parseInt(userId));
		
		jdbcTemplate.update(sql, parameterSource);
		
	}

	public void deleteUser(String userId) {
		
		String sql="delete from profile where userId=:userId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("userId",Integer.parseInt(userId));
		jdbcTemplate.update(sql, parameterSource);
		
	}

	public List<LoanApplicantModel> getAllLoans() {
	
		String sql="select * from loanmodel where status='In Process'";
		List<LoanApplicantModel> list=jdbcTemplate.query(sql, new LoanModelMapper());
		
				
		return list;
	}

	public List<AdminModel> getAllRequest() {
		
		String sql="select * from requestadmin";
		List<AdminModel> list=jdbcTemplate.query(sql, new AdminModelMapper());
		
		return list;
	}

	public void deleteRequest(String requestId) {
		String sql="delete from requestadmin where requestId=:requestId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("requestId",Integer.parseInt(requestId));
		jdbcTemplate.update(sql, parameterSource);
		
	}

	public void addRequest(String requestId) {
	
		String sql="select * from requestadmin where requestId=:requestId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("requestId",Integer.parseInt(requestId));
		List<AdminModel> lists=jdbcTemplate.query(sql, parameterSource, new AdminModelMapper());
		String email="";
		String password="";
		String mobile="";
		String userRole="";
		for(AdminModel adminModel: lists)
		{
			email= adminModel.getEmail();
			password=adminModel.getPassword();
			mobile=adminModel.getMobileNumber();
			userRole=adminModel.getUserRole();
		}
		
		String sql2="insert into usermodel(email,password,mobile,userRole) values(:email,:password,:mobile,:userRole)";
		SqlParameterSource parameterSource2=new MapSqlParameterSource("email",email)
				.addValue("password", password).addValue("mobile", mobile).addValue("userRole", userRole);
		jdbcTemplate.update(sql2, parameterSource2);
		
		String sql3="delete from requestadmin where requestId=:requestId";
		jdbcTemplate.update(sql3, parameterSource);
		
		
		
	}

	public void addReason(reason pro) {
		
		String sql="insert into reason(loanId,reason,adminId) values(:loanId,:reason,:adminId)";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",pro.getLoanId())
				.addValue("reason", pro.getReason()).addValue("adminId", pro.getAdminId());
		jdbcTemplate.update(sql, parameterSource);
		
		String sql2="update loanmodel set status='Rejected' where loanId=:loanId";
		SqlParameterSource parameterSource2=new MapSqlParameterSource("loanId",pro.getLoanId());
		jdbcTemplate.update(sql2, parameterSource2);
		
	}

	public List<LoanApplicantModel> getAllCheckedLoans() {
		
		String sql="select * from loanmodel where status!='In Process'";
		List<LoanApplicantModel> list=jdbcTemplate.query(sql, new LoanModelMapper());
		
				
		return list;
		
	}

	public List<reason> getAllReason() {
		
		String sql="select * from reason";
		List<reason> list=jdbcTemplate.query(sql, new ReasonMapper());
		
		return list;
	}

	public List<UserModel> getAllUser() {
	
		String sql="select * from usermodel";
		List<UserModel> list=jdbcTemplate.query(sql, new UserModelMapper());
		return list;
	}
	
	static float calculateEMI(int amount,float rate,int months)
	{
		float emi=0;
		emi=(float) (amount*rate*Math.pow((1+rate), months)/(Math.pow((1+rate), months)-1));
		return emi;
	}

	public void generateSchedule(String loanId) {
		
		String sql="select * from loanmodel where loanId=:loanId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId));
		List<LoanApplicantModel> list=jdbcTemplate.query(sql, parameterSource,new LoanModelMapper());
		String amount="";
		String months="";
		float rate=(float) (0.08/12);
		
		for(LoanApplicantModel applicantModel: list)
		{
			amount=applicantModel.getLoanAmountRequired();
			months=applicantModel.getLoanRepaymentMonths();
		}
		
		float emi=calculateEMI(Integer.parseInt(amount), rate, Integer.parseInt(months));
		System.out.println("EMI is Rs "+emi);
		String EMI=String.valueOf(emi);
		System.out.println(EMI);
		
		String sql3="update loanmodel set status='Approved' where loanId=:loanId";
		jdbcTemplate.update(sql3, parameterSource);
		
		int Months=Integer.parseInt(months);		
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.add(Calendar.MONTH, 1);
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 for(int i=1;i<=Months;i++)
		 {
			 
			 String dateWithoutTime = sdf.format(calendar.getTime());
			 String sql2="insert into repayment(loanId,installmentNo,emi,fine,total,dueDate) values(:loanId,:installmentNo,:emi,:fine,:total,:dueDate)";
			 SqlParameterSource parameterSource2=new MapSqlParameterSource("loanId",Integer.parseInt(loanId))
					 .addValue("installmentNo", i).addValue("emi", EMI).addValue("fine", "0").addValue("total", EMI)
					 .addValue("dueDate", dateWithoutTime);
				System.out.println(dateWithoutTime);
				jdbcTemplate.update(sql2, parameterSource2);
				
				 calendar.add(Calendar.MONTH, 1);
				
		 }
		
	}

	public List<repayment> getSchedule(String loanId) {
		
		String sql="select * from repayment where loanId=:loanId order by installmentNo";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId));
		List<repayment> list=jdbcTemplate.query(sql, parameterSource,new RepaymentMapper());
		
		for(repayment repay: list)
		{
			System.out.println(dayDiff(repay.getDueDate(), "21-03-2022"));
		}
		
		return list;
		
	}

	public List<LoanApplicantModel> getThisLoan(String loanId) {
		
		String sql="select * from loanmodel where loanId=:loanId";
		SqlParameterSource parameterSource=new MapSqlParameterSource("loanId",Integer.parseInt(loanId));
		List<LoanApplicantModel> list=jdbcTemplate.query(sql, parameterSource, new LoanModelMapper());
		
		return list;
	}
	
	public int dayDiff(String date1, String date2)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		Date d1=null;
		Date d2=null;
		
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
		}
		 catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long diff = d2.getTime() - d1.getTime();
		
		int daysDiff = (int) (diff / (1000 * 60 * 60* 24));
		
		return daysDiff;
		
	}
	
	static float fineEMI(int diff, float emi)
	{
		float rate=(float) (0.04/30);
		float extra=(float) (rate*emi*diff);
	//	float newBalance=balance+extra;
		return emi+extra;
	}
	
	
	
	
	
	

}
