package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;   // ROLE_USER, ROLE_ADMIN
	// 휴면 계정 설정을 위한 로그인 날짜 필드
	// private Timestamp loginDate;
	@CreationTimestamp
	private Timestamp createDate;
}
