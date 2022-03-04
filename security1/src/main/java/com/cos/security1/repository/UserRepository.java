package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaRepository가 들고있음
// @Repository 어노테이션 없이도 IoC -> JpaRepository 상속했기때문!!
public interface UserRepository extends JpaRepository<User, Integer>{

}
