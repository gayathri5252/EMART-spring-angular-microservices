package com.project.emart.service;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.emart.dao.BuyerSignupDao;
import com.project.emart.entity.BillDetailsEntity;
import com.project.emart.entity.BillEntity;
import com.project.emart.entity.BuyerSignupEntity;
import com.project.emart.entity.CategoryEntity;
import com.project.emart.entity.ItemEntity;
import com.project.emart.entity.SellerSignupEntity;
import com.project.emart.entity.SubCategoryEntity;
import com.project.emart.pojo.BillPojo;
import com.project.emart.pojo.BillerDetailsPojo;
import com.project.emart.pojo.BuyerSignupPojo;
import com.project.emart.pojo.CategoryPojo;
import com.project.emart.pojo.ItemPojo;
import com.project.emart.pojo.SellerSignupPojo;
import com.project.emart.pojo.SubCategoryPojo;

@Service
public class BuyerSignupServiceImpl implements BuyerSignupService {
	static Logger LOG = Logger.getLogger(BuyerSignupServiceImpl.class.getClass());

	@Autowired
	BuyerSignupDao buyerDao;

	//Implementation of Buyer validation 
	@Override
	public BuyerSignupPojo validateBuyer(BuyerSignupPojo buyerSignupPojo) {
		LOG.info("Enterd validateBuyer()");
		BuyerSignupEntity buyerSignupEntity = buyerDao.findByUsernameAndPassword(buyerSignupPojo.getUsername(),
				buyerSignupPojo.getPassword());
		if (buyerSignupEntity != null) {
			Set<BillEntity> allBills = buyerSignupEntity.getAllBills();
			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();
			// Set<BillerDetailsPojo> allBillDetailsPojo = new HashSet<BillerDetailsPojo>();
			for (BillEntity billEntity : allBills) {
				Set<BillerDetailsPojo> allBillDetailsPojo = new HashSet<BillerDetailsPojo>();
				Set<BillDetailsEntity> allBillDetailsEntity = billEntity.getAllBillDetails();
				for (BillDetailsEntity billDetailsEntity : allBillDetailsEntity) {
					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubCategoryId();
					CategoryEntity categoryEntity = subCategoryEntity.getCategoryId();
					SellerSignupEntity sellerSignupEntity = itemEntity.getSellerId();
					SellerSignupPojo sellersignupPojo = new SellerSignupPojo(sellerSignupEntity.getId(),
							sellerSignupEntity.getUsername(), sellerSignupEntity.getPassword(),
							sellerSignupEntity.getCompany(), sellerSignupEntity.getBrief(), sellerSignupEntity.getGst(),
							sellerSignupEntity.getAddress(), sellerSignupEntity.getEmail(),
							sellerSignupEntity.getWebsite(), sellerSignupEntity.getContact());

					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
							categoryEntity.getBrief());
					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
							subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
							subCategoryEntity.getGstPercent());

					ItemPojo itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), subCategoryPojo,
							itemEntity.getPrice(), itemEntity.getDescription(), itemEntity.getStock(),
							itemEntity.getRemarks(), itemEntity.getImage(), sellersignupPojo);
					BillerDetailsPojo billDetailsPojo = new BillerDetailsPojo(billDetailsEntity.getId(), null,
							itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(billEntity.getId(), null, billEntity.getType(), billEntity.getDate(),
						billEntity.getRemarks(), billEntity.getAmount(), allBillDetailsPojo);
				allBillPojo.add(billPojo);
			}
			buyerSignupPojo = new BuyerSignupPojo(buyerSignupEntity.getId(), buyerSignupEntity.getUsername(),
					buyerSignupEntity.getPassword(), buyerSignupEntity.getEmail(), buyerSignupEntity.getMobile(),
					buyerSignupEntity.getDate(), allBillPojo);

		}
		LOG.info("Exited validateBuyer()");

		return buyerSignupPojo;
	}

	//Implementing service to Retrieving Buyer
	@Override
	public BuyerSignupPojo getBuyer(int BuyerId) {
		BuyerSignupPojo buyersignupPojo = null;
		BuyerSignupEntity buyerEntity = buyerDao.findById(BuyerId).get();
		if (buyerEntity != null) {
			Set<BillEntity> allBills = buyerEntity.getAllBills();
			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();

			for (BillEntity billEntity : allBills) {
				Set<BillerDetailsPojo> allBillDetailsPojo = new HashSet<BillerDetailsPojo>();
				Set<BillDetailsEntity> allBillDetailsEntity = billEntity.getAllBillDetails();
				for (BillDetailsEntity billDetailsEntity : allBillDetailsEntity) {
					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubCategoryId();
					CategoryEntity categoryEntity = subCategoryEntity.getCategoryId();
					SellerSignupEntity sellerSignupEntity = itemEntity.getSellerId();
					SellerSignupPojo sellersignupPojo = new SellerSignupPojo(sellerSignupEntity.getId(),
							sellerSignupEntity.getUsername(), sellerSignupEntity.getPassword(),
							sellerSignupEntity.getCompany(), sellerSignupEntity.getBrief(), sellerSignupEntity.getGst(),
							sellerSignupEntity.getAddress(), sellerSignupEntity.getEmail(),
							sellerSignupEntity.getWebsite(), sellerSignupEntity.getContact());

					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
							categoryEntity.getBrief());
					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
							subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
							subCategoryEntity.getGstPercent());

					ItemPojo itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), subCategoryPojo,
							itemEntity.getPrice(), itemEntity.getDescription(), itemEntity.getStock(),
							itemEntity.getRemarks(), itemEntity.getImage(), sellersignupPojo);
					BillerDetailsPojo billDetailsPojo = new BillerDetailsPojo(billDetailsEntity.getId(), null,
							itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(billEntity.getId(), null, billEntity.getType(), billEntity.getDate(),
						billEntity.getRemarks(), billEntity.getAmount(), allBillDetailsPojo);
				allBillPojo.add(billPojo);
			}
			buyersignupPojo = new BuyerSignupPojo(buyerEntity.getId(), buyerEntity.getUsername(),
					buyerEntity.getPassword(), buyerEntity.getEmail(), buyerEntity.getMobile(), buyerEntity.getDate(),
					allBillPojo);

		}
		BasicConfigurator.configure();

		return buyersignupPojo;
	}
}
