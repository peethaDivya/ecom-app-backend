package com.ouhamza.ecommerce.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ouhamza.ecommerce.entity.Grocery;



public interface GroceryRepository extends JpaRepository<Grocery, Long> {

}