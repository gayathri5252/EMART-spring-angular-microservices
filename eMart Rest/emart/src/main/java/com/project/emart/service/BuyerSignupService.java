package com.project.emart.service;

import com.project.emart.pojo.BuyerSignupPojo;

public interface BuyerSignupService {
	
		BuyerSignupPojo validateBuyer(BuyerSignupPojo buyerSignupPojo);

		BuyerSignupPojo getBuyer(int buyerId);
		
}
