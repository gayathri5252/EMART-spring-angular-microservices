package com.project.emart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.emart.entity.SellerSignupEntity;

@Repository
public interface SellerSignupDao extends JpaRepository<SellerSignupEntity, Integer> {

}
