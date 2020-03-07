package com.project.emart.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.emart.pojo.BillPojo;
import com.project.emart.service.BillService;

@RestController
@CrossOrigin
@RequestMapping("emart")
public class BillController {
	static Logger LOG = Logger.getLogger(BillController.class.getClass());

	@Autowired
	BillService billService;
	
	//EndPoint to addBill
	@PostMapping("/bitem")
	BillPojo addBill(@RequestBody BillPojo billPojo) {
		LOG.info("Entered the end point \'emart/bitem\'");
		LOG.info("exited end point \'emart/bitem\'");
		return billService.addBill(billPojo);
	}

}

