package com.project.emart.service;

import java.util.List;

import com.project.emart.pojo.ItemPojo;

public interface ItemService {
	
	List<ItemPojo> getAllItems();
	ItemPojo getItem(int ItemId);	
	
}
