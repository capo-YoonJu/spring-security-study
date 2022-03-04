package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
	
	// SecurityConfig에서 @EnableGlobalMethodSecurity(securedEnabled = true) 어노테이션에 의해 활성화됨. 간단하게 이 메서드를 특정 권한만 요청할 수 있도록 함
	// SecurityConfig의 configure() 메서드 내부에서 체이닝으로 설정하는 것은 글로벌한 설정이고
	// @Secured()는 메서드에만 간단하게 권한 설정하는 것임
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인 정보";
	}
	
	// SecurityConfig에서 @EnableGlobalMethodSecurity(prePostEnabled = true) 어노테이션에 의해 활성화됨. 이 메서드가 실행되기 전에 조건을 검사
	// 이 메서드가 실행되고 난 뒤 조건검사 하고싶으면 @PostAuthorize() 하면 됨
	// 일반적으로 @PreAuthorize 보다는 @Secured()로 검사하는 경우가 많음
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")     // 권한 여러개 설정하기 위해 hasRole() or hasRole()
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터 정보";
	}
}
 