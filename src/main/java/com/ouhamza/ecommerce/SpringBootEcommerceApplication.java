package com.ouhamza.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.ouhamza.ecommerce.beans.DbManager;
import com.ouhamza.ecommerce.beans.Users;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"com.ouhamza.ecommerce"})
@RestController
public class SpringBootEcommerceApplication {
   
	@RequestMapping(value = "/viewData",method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public ArrayList<Users> getData()
	{
		//Get the data from DB and assign to Users and return
		
		ArrayList<Users> list = new ArrayList<Users>();
		DbManager db = new DbManager(0);
		try {
		ResultSet rs = db.getResultSet("Select * from register_detail");
		while(rs.next()) {
			Users usr = new Users();
			//firstname, lastname, email, phoneno, location, address, username, password
			String firstname = rs.getString("firstname");
			String lastname = rs.getString("lastname");
			String email = rs.getString("email");
			String phone_no = rs.getString("phone_no");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String register_id = rs.getString("register_id");
			usr.setFirstname(firstname);
			usr.setLastname(lastname);
			usr.setEmail(email);
			usr.setPhoneno(phone_no);
			usr.setUsername(username);
			usr.setPassword(password);
			usr.setregisterId(register_id);
			
			list.add(usr);
			
		}
		}catch(Exception e) {
			Users usr = new Users();
			//usr.setAddress(e.getMessage());
			list.add(usr);
		}
		return list;
	}
	//********************************************************************************
	
	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	@CrossOrigin(origins = {"*"})
	public Users insertData(@RequestBody Users usr)
	{
	
	String resp = "not inserted";
	if(usr == null) {

	}else {
	//DBManager code to insert
	DbManager db = new DbManager(0);
	String[] values = {usr.getFirstname(), usr.getLastname(), usr.getEmail(), usr.getPhoneno(), usr.getUsername(), usr.getPassword(), usr.getregisterid()};
	           
	           
	           
	               String query = "insert into register_detail (firstname,lastname,email,phone_no,username,password,register_id) values (?,?,?,?,?,?,?)";
	               
	               
	            int j= db.insertItems(query, values,"register_id");
	            if(j!=0) {
	            resp = "inserted successfully";
	            String registerid = Integer.toString(j);
	            //usr.setregisterId(register_id);
	            }
	//db.insertItems(query, item);
	}

	return usr ;
	}
	@RequestMapping(value = "/authUser",method = RequestMethod.POST)
	@CrossOrigin(origins = {"*"})
	
	public Users auth_Users(@RequestBody Users users)
	{ 
		Users usr = new Users();
		try {
			
			DbManager db = new DbManager(0);
			ResultSet rs = db.getResultSet("select * from register_detail where username='"+users.getUsername()+"' "
					+ "and password='"+users.getPassword()+"'");
			if(rs.next()) {
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				String phone_no = rs.getString("phone_no");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String register_id = rs.getString("register_id");
				usr.setFirstname(firstname);
				usr.setLastname(lastname);
				usr.setEmail(email);
				usr.setPhoneno(phone_no);
				usr.setUsername(username);
				usr.setPassword(password);
				usr.setregisterId(register_id);
			}else {
				//do nothing sent  empty response
				usr.setFirstname("nouser");
				usr.setLastname("nouser");
			}
		}catch(Exception e) {
			
		}
		return usr;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEcommerceApplication.class, args);
	}

}
