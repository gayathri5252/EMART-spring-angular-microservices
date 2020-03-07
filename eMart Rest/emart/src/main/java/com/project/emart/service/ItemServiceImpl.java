package com.project.emart.service;

import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.emart.dao.ItemDao;
import com.project.emart.entity.CategoryEntity;
import com.project.emart.entity.ItemEntity;
import com.project.emart.entity.SellerSignupEntity;
import com.project.emart.entity.SubCategoryEntity;
import com.project.emart.pojo.CategoryPojo;
import com.project.emart.pojo.ItemPojo;
import com.project.emart.pojo.SellerSignupPojo;
import com.project.emart.pojo.SubCategoryPojo;

@Service
public class ItemServiceImpl implements ItemService {
	static Logger LOG = Logger.getLogger(ItemServiceImpl.class.getClass());

	@Autowired
	ItemDao itemDao;

	//Implementation to Retrieve ParticularItem 
	@Override
	public ItemPojo getItem(int itemId) {
		LOG.info("Enterd getItem()");
		ItemPojo itemPojo = null;
		Optional result = itemDao.findById(itemId);
		if (result.isPresent()) {
			ItemEntity itemEntity = (ItemEntity) result.get();
			SubCategoryEntity subCategoryEntity = itemEntity.getSubCategoryId();
			CategoryEntity categoryEntity = subCategoryEntity.getCategoryId();
			SellerSignupEntity sellerSignupEntity = itemEntity.getSellerId();

			CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
					categoryEntity.getBrief());
			SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
					subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
					subCategoryEntity.getGstPercent());
			SellerSignupPojo sellerSignupPojo = new SellerSignupPojo(sellerSignupEntity.getId(),
					sellerSignupEntity.getUsername(), sellerSignupEntity.getPassword(), sellerSignupEntity.getCompany(),
					sellerSignupEntity.getBrief(), sellerSignupEntity.getGst(), sellerSignupEntity.getAddress(),
					sellerSignupEntity.getEmail(), sellerSignupEntity.getWebsite(), sellerSignupEntity.getContact());
			itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), subCategoryPojo, itemEntity.getPrice(),
					itemEntity.getDescription(), itemEntity.getStock(), itemEntity.getRemarks(), itemEntity.getImage(),
					sellerSignupPojo);
		}
		LOG.info("Enterd getItem()");

		return itemPojo;
	}

	//Implementation to retrieve allItems
	@Override
	public List<ItemPojo> getAllItems() {
		LOG.info("Enterd getAllItems()");
		List<ItemPojo> allItemPojo = new ArrayList();
		Iterable<ItemEntity> allItemEntity = itemDao.findAll();
		Iterator<ItemEntity> itr = allItemEntity.iterator();

		while (itr.hasNext()) {

			ItemEntity itemEntity = (ItemEntity) itr.next();
			SubCategoryEntity subCategoryEntity = itemEntity.getSubCategoryId();
			CategoryEntity categoryEntity = subCategoryEntity.getCategoryId();
			SellerSignupEntity sellerSignupEntity = itemEntity.getSellerId();

			CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getId(), categoryEntity.getName(),
					categoryEntity.getBrief());
			SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getId(),
					subCategoryEntity.getName(), categoryPojo, subCategoryEntity.getBrief(),
					subCategoryEntity.getGstPercent());
			SellerSignupPojo sellerSignupPojo = new SellerSignupPojo(sellerSignupEntity.getId(),
					sellerSignupEntity.getUsername(), sellerSignupEntity.getPassword(), sellerSignupEntity.getCompany(),
					sellerSignupEntity.getBrief(), sellerSignupEntity.getGst(), sellerSignupEntity.getAddress(),
					sellerSignupEntity.getEmail(), sellerSignupEntity.getWebsite(), sellerSignupEntity.getContact());

			ItemPojo itemPojo = new ItemPojo(itemEntity.getId(), itemEntity.getName(), subCategoryPojo,
					itemEntity.getPrice(), itemEntity.getDescription(), itemEntity.getStock(), itemEntity.getRemarks(),
					itemEntity.getImage(), sellerSignupPojo);
			allItemPojo.add(itemPojo);
		}

		LOG.info("Enterd getAllItems()");
		BasicConfigurator.configure();

		return allItemPojo;

	}

}
