package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소 요청이 오면 낚아채 로그인을 진행해줌
// 로그인 진행이 완료되면 security session을 만들어줌 (Security ContextHolder 라는 키값에 세션 정보를 저장)
// Security Session에 저장할 수 있는 오브젝트 타입이 정해져있음 => Authentication 타입 객체
// Authentication 객체 안에는 User 정보가 있어야 함
// User 오브젝트 타입 -> UserDetails 타입 객체

// Security Session -> Authentication 타입 객체 가짐 -> UserDetails 타입 객체 가짐

public class PrincipalDetails implements UserDetails{

	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 만약 1년간 로긴 안해서 휴면 계정으로 변환될 때 이 메서드 안에서 비활성화 시킬 수 있음
		// User 오브젝트 타입에 loginDate 라는 필드 추가해서 로긴할때마다 날짜 저장해둠
		// 현재날짜 - 로긴날짜 > 1년 -> return false 로 분기해주면 됨
		return true;
	}

}
