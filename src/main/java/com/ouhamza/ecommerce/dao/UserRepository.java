package com.ouhamza.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ouhamza.ecommerce.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
