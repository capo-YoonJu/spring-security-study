package com.cos.security1.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채 로그인을 진행해줌
// 로그인 진행이 완료되면 security session을 만들어줌 (Security ContextHolder 라는 키값에 세션 정보를 저장)
// Security Session에 저장할 수 있는 오브젝트 타입이 정해져있음 => Authentication 타입 객체
// Authentication 객체 안에는 User 정보가 있어야 함
// User 오브젝트 타입 -> UserDetails 타입 객체

// Security Session -> Authentication 타입 객체 -> UserDetails 타입 객체

public class PrincipalDetails implements UserDetails{

}
