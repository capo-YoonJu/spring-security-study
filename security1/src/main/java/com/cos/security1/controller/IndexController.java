package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// localhost:8080
	// localhost:8080/
	@GetMapping({"", "/"})
	public String index() {
		// mustache 기본 폴더 : src/main/resources/
		// viewResolver 설정 : templates (prefix), .mustache (suffix)   // 생략 가능
		return "index";    // src/main/resources/templates/index.mustache 로 잡음
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// 최초에는 Spring Security가 /login을 낚아채서 미리 구현된 로그인 페이지로 넘김 -> SecurityConfig 파일 생성 및 설정 후 낚아채지 않음
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user);     // 회원가입은 잘되는데 이러면 안됨. -> 시큐리티 로긴 불가능. 패스워드 암호화가 안되어있기때문
		return "redirect:/loginForm";
	}
}
 