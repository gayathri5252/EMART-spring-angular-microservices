package com.project.emart.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.emart.controller.BillController;
import com.project.emart.dao.BillDao;
import com.project.emart.dao.BillDetails;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@Service
public class BillServiceImpl implements BillService {
	static Logger LOG = Logger.getLogger(BillServiceImpl.class.getClass());

	@Autowired
	BillDao billDao;
	@Autowired
	BillDetails billDetailsDao;

	//Implementing  addBill function
	@Override
	public BillPojo addBill(BillPojo billPojo) {
		LOG.info("Enterd addBill()");
		BuyerSignupPojo buyerPojo = billPojo.getBuyerId();
		BuyerSignupEntity buyerEntity = new BuyerSignupEntity(buyerPojo.getId(), buyerPojo.getUsername(),
				buyerPojo.getPassword(), buyerPojo.getEmail(), buyerPojo.getMobile(), buyerPojo.getDate(), null);

		BillEntity billEntity = new BillEntity();
		billEntity.setId(0);
		billEntity.setAmount(billPojo.getAmount());
		billEntity.setRemarks(billPojo.getRemarks());
		billEntity.setType(billPojo.getType());
		billEntity.setDate(billPojo.getDate());
		billEntity.setBuyer(buyerEntity);

		billEntity = billDao.saveAndFlush(billEntity);
		billPojo.setId(billEntity.getId());

		BillEntity setBillEntity = billDao.findById(billEntity.getId()).get();

		Set<BillDetailsEntity> allBillDetails = new HashSet<BillDetailsEntity>();
		Set<BillerDetailsPojo> allBillDetailsPojo = billPojo.getAllBillDetails();
		for (BillerDetailsPojo billDetailsPojo : allBillDetailsPojo) {
			ItemPojo itemPojo = billDetailsPojo.getItemId();
			SubCategoryPojo subcategoryPojo = itemPojo.getSubCategoryId();
			CategoryPojo categoryPojo = subcategoryPojo.getCategoryId();
			SellerSignupPojo sellerPojo = itemPojo.getSeller_id();
			SellerSignupEntity sellerSignupEntity = new SellerSignupEntity(sellerPojo.getId(), sellerPojo.getUsername(),
					sellerPojo.getPassword(), sellerPojo.getCompany(), sellerPojo.getGst(), sellerPojo.getBrief(),
					sellerPojo.getAddress(), sellerPojo.getEmail(), sellerPojo.getWebsite(), sellerPojo.getContact());
			CategoryEntity categoryEntity = new CategoryEntity(categoryPojo.getId(), categoryPojo.getName(),
					categoryPojo.getBrief());
			SubCategoryEntity subCategoryEntity = new SubCategoryEntity(subcategoryPojo.getId(),
					subcategoryPojo.getName(), categoryEntity, subcategoryPojo.getBrief(),
					subcategoryPojo.getGstPercent());

			ItemEntity itemEntity = new ItemEntity(itemPojo.getId(), itemPojo.getName(), subCategoryEntity,
					itemPojo.getPrice(), itemPojo.getDescription(), itemPojo.getStock(), itemPojo.getRemarks(),
					itemPojo.getImage(), sellerSignupEntity);
			BillDetailsEntity billDetailsEntity = new BillDetailsEntity(billDetailsPojo.getId(), setBillEntity,
					itemEntity);

			billDetailsDao.save(billDetailsEntity);

		}
		BasicConfigurator.configure();
		LOG.info("Exited addBill()");

		return billPojo;
	}

}
