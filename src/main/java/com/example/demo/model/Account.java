package com.example.demo.model;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@SessionScope
public class Account {


	private String userName;


	public Account() {
	}


	public Account(String userName) {
		this.userName = userName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
