package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

    private final UserRepository userRepository;
	
	private final HttpSession session;
	private final Account account;
	
	public UserController(HttpSession session, Account account, UserRepository userRepository) {
		this.session = session;
		this.account = account;
		this.userRepository = userRepository;
	}
	
	//ログイン画面
	@GetMapping({"/", "/login"})
	public String index() {
		session.invalidate();
		return "login";
	}
	
	//ログイン機能
	@PostMapping("/login")
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {
		
		List<User> userList = userRepository.findByEmailAndPassword(email, password);
		if(userList == null || userList.size() == 0) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
		}
		
		User user = userList.get(0);
		account.setUserName(user.getUserName());
		account.setId(user.getId());
		
		return "redirect:/items";
	}
	
	//新規登録画面
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	//新規登録機能
	@PostMapping("/register")
	public String add(
			@RequestParam String userName,
			@RequestParam String email,
			@RequestParam String password,
			Model model) {
			
			List<String> errorList = new ArrayList<>();
			if(userName.length() == 0) {
				errorList.add("ユーザー名は必須です");
			}
			if(email.length() == 0) {
				errorList.add("メールアドレスは必須です");
			}
			List<User> userList = userRepository.findByEmail(email);
			if(userList != null && userList.size() > 0) {
				errorList.add("登録済みのメールアドレスです");
			}
			if(password.length() == 0) {
				errorList.add("パスワードは必須です");
			}
			
			if(errorList.size() > 0) {
				model.addAttribute("errorList", errorList);
				model.addAttribute("userName", userName);
				model.addAttribute("email", email);
				model.addAttribute("password", password);
				return "register";
			}
			
			User user = new User(userName, email, password);
			userRepository.save(user);
			return "redirect/login";
		}

}
